/*
 * Copyright 2020 ChenJun (power4j@outlook.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.power4j.flygon.common.captcha.filter;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.power4j.flygon.common.captcha.model.CodeRequest;
import com.power4j.flygon.common.captcha.model.CodeValidateRequest;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.ji.common.core.util.DateTimeUtil;
import com.power4j.ji.common.core.util.HttpServletResponseUtil;
import com.power4j.kit.captcha.SpecCaptcha;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 验证码生成和校验
 *
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/8
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class CaptchaFilter extends OncePerRequestFilter implements Ordered {

	private final static int INTERNAL_FONTS = 10;

	private final static String CACHE_CAPTCHA = "captcha";

	private final static String PARAM_KEY_REQ_ID = "reqToken";

	private final static String PARAM_KEY_CODE = "code";

	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	private final UrlPathHelper pathHelper = new UrlPathHelper();

	@Setter
	private ObjectMapper objectMapper = new ObjectMapper();

	@Setter
	private String codeUrl = "/code";

	@Setter
	private List<String> validateUrls;

	@Setter
	private long expireSeconds = 300L;

	private final CacheManager cacheManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (requiresCodeGenerate(request, response)) {
			handleCodeGenerate(request, response);
			return;
		}
		else if (requiresCodeValidate(request, response)) {
			if (handleCodeValidate(request, response)) {
				filterChain.doFilter(request, response);
			}
		}
		else {
			filterChain.doFilter(request, response);
		}
	}

	protected boolean requiresCodeGenerate(HttpServletRequest request, HttpServletResponse response) {
		return (codeUrl == null || codeUrl.isEmpty()) ? false
				: antPathMatcher.match(codeUrl, pathHelper.getPathWithinApplication(request));
	}

	protected boolean requiresCodeValidate(HttpServletRequest request, HttpServletResponse response) {
		if (validateUrls == null || validateUrls.isEmpty()) {
			return false;
		}
		final String path = pathHelper.getPathWithinApplication(request);
		return validateUrls.stream().anyMatch(str -> antPathMatcher.match(str, path));
	}

	protected void handleCodeGenerate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CodeRequest codeRequest = getCodeRequest(request);
		if (codeRequest == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		SpecCaptcha captcha = new SpecCaptcha(130, 48);
		try {
			captcha.setFont(RandomUtil.randomInt(Short.MAX_VALUE) % INTERNAL_FONTS);
		}
		catch (FontFormatException e) {
			throw new IOException(e.getMessage(), e);
		}

		getCaptchaCache().put(codeRequest.getReqToken(), createCodeInfo(captcha.text()));
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		HttpServletResponseUtil.setNoCache(response);
		captcha.out(response.getOutputStream());
	}

	protected boolean handleCodeValidate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
			HttpServletResponseUtil.writeJson(objectMapper, response, ApiResponseUtil.fail("不支持该HTTP方法"),
					HttpStatus.OK);
			return false;
		}
		CodeValidateRequest codeValidateRequest = getCodeValidateRequest(request);
		if (codeValidateRequest == null || codeValidateRequest.getReqToken() == null
				|| codeValidateRequest.getCode() == null) {
			HttpServletResponseUtil.writeJson(objectMapper, response,
					ApiResponse.of(SysErrorCodes.E_PARAM_MISS, "验证码参数不正确"), HttpStatus.OK);
			return false;
		}
		CodeInfo codeInfo = retrieveCodeInfo(codeValidateRequest.getReqToken()).orElse(null);
		if (!validateCode(codeInfo, codeValidateRequest.getCode(), expireSeconds)) {
			HttpServletResponseUtil.writeJson(objectMapper, response,
					ApiResponseUtil.fail("验证码错误:" + codeValidateRequest.getCode()), HttpStatus.OK);
			return false;
		}
		return true;
	}

	@Nullable
	protected CodeRequest getCodeRequest(HttpServletRequest request) {
		String reqToken = request.getParameter(PARAM_KEY_REQ_ID);
		if (reqToken == null || reqToken.isEmpty()) {
			return null;
		}
		CodeRequest codeRequest = new CodeRequest();
		codeRequest.setReqToken(reqToken);
		return codeRequest;
	}

	protected CodeValidateRequest getCodeValidateRequest(HttpServletRequest request) {

		String reqToken = request.getParameter(PARAM_KEY_REQ_ID);
		if (reqToken == null || reqToken.isEmpty()) {
			return null;
		}
		String code = request.getParameter(PARAM_KEY_CODE);
		if (code == null || code.isEmpty()) {
			return null;
		}
		CodeValidateRequest codeValidateRequest = new CodeValidateRequest();
		codeValidateRequest.setCode(code);
		codeValidateRequest.setReqToken(reqToken);
		return codeValidateRequest;
	}

	protected final Cache getCaptchaCache() {
		return cacheManager.getCache(String.format("%s#%ds", CACHE_CAPTCHA, expireSeconds));
	}

	protected final CodeInfo createCodeInfo(String code) {
		CodeInfo codeInfo = new CodeInfo();
		codeInfo.setCode(code);
		codeInfo.setCreateTime(DateTimeUtil.utcNow());
		return codeInfo;
	}

	protected Optional<CodeInfo> retrieveCodeInfo(String key) {
		Cache cache = getCaptchaCache();
		CodeInfo codeInfo = cache.get(key, CodeInfo.class);
		cache.evict(key);
		return Optional.ofNullable(codeInfo);
	}

	protected boolean validateCode(@Nullable CodeInfo codeInfo, String code, long ttl) {
		if (codeInfo == null) {
			return false;
		}
		return codeInfo.getCode().equalsIgnoreCase(code)
				&& Duration.between(codeInfo.getCreateTime(), DateTimeUtil.utcNow()).getSeconds() <= ttl;
	}

	@Override
	public int getOrder() {
		return OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER - 101;
	}

	@Data
	public static class CodeInfo implements Serializable {

		private final static long serialVersionUID = 1L;

		private String code;

		private LocalDateTime createTime;

	}

}
