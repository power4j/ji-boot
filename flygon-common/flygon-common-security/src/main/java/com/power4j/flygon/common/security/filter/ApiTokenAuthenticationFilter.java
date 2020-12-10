package com.power4j.flygon.common.security.filter;

import cn.hutool.core.util.StrUtil;
import com.power4j.flygon.common.core.constant.SecurityConstant;
import com.power4j.flygon.common.security.auth.ApiTokenAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/22
 * @since 1.0
 */
@Slf4j
public class ApiTokenAuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext.getAuthentication() == null || !securityContext.getAuthentication().isAuthenticated()) {
			String tokenValue = getApiTokenValue(request);
			if (tokenValue != null && !tokenValue.isEmpty()) {
				ApiTokenAuthentication apiTokenAuthentication = new ApiTokenAuthentication(tokenValue.trim());
				SecurityContextHolder.getContext().setAuthentication(apiTokenAuthentication);
			}
			request.setAttribute(SecurityConstant.TOKEN_ATTRIBUTE_KEY, true);
		}
		filterChain.doFilter(request, response);
	}

	protected String getApiTokenValue(HttpServletRequest request) {
		String value = request.getHeader(SecurityConstant.HEADER_TOKEN_KEY);
		if (StrUtil.isNotBlank(value)) {
			log.trace("请求头中的{}:{}", SecurityConstant.HEADER_TOKEN_KEY, value);
			return value;
		}
		String[] values = request.getParameterValues(SecurityConstant.PARAMETER_TOKEN_KEY);
		if (values != null && values.length > 0) {
			log.trace("请求参数的{}:{}", SecurityConstant.HEADER_TOKEN_KEY, values);
			return values[0];
		}
		return null;
	}

}
