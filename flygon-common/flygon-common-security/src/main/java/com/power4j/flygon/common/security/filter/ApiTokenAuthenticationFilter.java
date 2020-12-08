package com.power4j.flygon.common.security.filter;

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
import java.util.Optional;

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
			String tokenValue = Optional.ofNullable(getTokenValueFromHeader(request))
					.orElse(getTokenValueFromParam(request));
			if (tokenValue != null && !tokenValue.isEmpty()) {
				ApiTokenAuthentication apiTokenAuthentication = new ApiTokenAuthentication(tokenValue);
				SecurityContextHolder.getContext().setAuthentication(apiTokenAuthentication);
			}
			request.setAttribute(SecurityConstant.TOKEN_ATTRIBUTE_KEY, true);
		}
		filterChain.doFilter(request, response);
	}

	private String getTokenValueFromHeader(HttpServletRequest request) {
		final String value = request.getHeader(SecurityConstant.HEADER_TOKEN_KEY);
		log.trace("请求头中的{}:{}",SecurityConstant.HEADER_TOKEN_KEY,value);
		return value;
	}

	private String getTokenValueFromParam(HttpServletRequest request) {
		String[] values = request.getParameterValues(SecurityConstant.PARAMETER_TOKEN_KEY);
		log.trace("请求参数的{}:{}",SecurityConstant.HEADER_TOKEN_KEY,values);
		return (values != null && values.length > 0) ? values[0] : null;
	}

}
