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

package com.power4j.ji.common.core.util;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * HttpServletRequest Util
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-17
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class HttpServletRequestUtil {

	public final static String UNKNOWN = "unknown";

	public final static String X_FORWARD_FOR = "x-forwarded-for";

	public final static String PROXY_CLIENT_IP = "Proxy-Client-IP";

	public final static String WL_Proxy_Client_IP = "WL-Proxy-Client-IP";

	public final static String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";

	public final static String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

	/**
	 * 获取当前请求
	 * @see RequestContextListener
	 * @return
	 */
	public Optional<HttpServletRequest> getCurrentRequest() {
		return Optional.ofNullable(RequestContextHolder.getRequestAttributes()).map(x -> (ServletRequestAttributes) x)
				.map(ServletRequestAttributes::getRequest);
	}

	/**
	 * 获取IP地址
	 * @see <a href=
	 * "https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/X-Forwarded-For">X-Forwarded-For</a>
	 * @param request
	 * @return
	 */
	public static Optional<String> getRemoteAddr(HttpServletRequest request) {
		String address = request.getHeader(X_FORWARD_FOR);
		if (CharSequenceUtil.isBlank(address) || UNKNOWN.equalsIgnoreCase(address)) {
			address = request.getHeader(PROXY_CLIENT_IP);
		}
		if (CharSequenceUtil.isBlank(address) || UNKNOWN.equalsIgnoreCase(address)) {
			address = request.getHeader(WL_Proxy_Client_IP);
		}
		if (CharSequenceUtil.isBlank(address) || UNKNOWN.equalsIgnoreCase(address)) {
			address = request.getHeader(HTTP_CLIENT_IP);
		}
		if (CharSequenceUtil.isBlank(address) || UNKNOWN.equalsIgnoreCase(address)) {
			address = request.getHeader(HTTP_X_FORWARDED_FOR);
		}
		if (CharSequenceUtil.isBlank(address) || UNKNOWN.equalsIgnoreCase(address)) {
			address = request.getRemoteAddr();
		}
		return Optional.ofNullable(address).map(s -> CharSequenceUtil.split(s, ",")[0]);
	}

	/**
	 * 获取IP地址
	 * @see #getRemoteAddr(HttpServletRequest)
	 * @param request
	 * @param defaultValue
	 * @return
	 */
	@Nullable
	public static String getRemoteAddr(HttpServletRequest request, @Nullable String defaultValue) {
		return getRemoteAddr(request).orElse(defaultValue);
	}

	/**
	 * 提取http头
	 * @param request
	 * @param names
	 * @return
	 */
	public HttpHeaders pickupHeaders(HttpServletRequest request, Collection<String> names) {
		return pickupHeaders(request, name -> names.contains(name));
	}

	/**
	 * 提取http头
	 * @param request
	 * @param predicate
	 * @return
	 */
	public HttpHeaders pickupHeaders(HttpServletRequest request, Predicate<String> predicate) {
		HttpHeaders headers = new HttpHeaders();
		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames == null) {
			return headers;
		}
		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			if (predicate.test(name)) {
				headers.add(name, request.getHeader(name));
			}
		}
		return headers;
	}

	/**
	 * 获取Header
	 * @param request
	 * @param name
	 * @return
	 */
	public Optional<String> getHeader(HttpServletRequest request, String name) {
		return Optional.ofNullable(request.getHeader(name));
	}

}
