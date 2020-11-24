package com.power4j.flygon.common.security.auth;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import com.power4j.flygon.common.security.msg.SecurityMessageSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/23
 * @since 1.0
 */
@Slf4j
public class ApiTokenAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final ObjectMapper jsonMapper = new ObjectMapper();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		log.debug("Handling {} : {}",authException.getClass().getSimpleName(),authException.getMessage());
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ApiResponse<?> result = ApiResponseUtil.fail(authException.getMessage());

		if (authException instanceof CredentialsExpiredException
				|| authException instanceof InsufficientAuthenticationException) {
			String msg = SecurityMessageSource.getAccessor().getMessage(
					"AbstractUserDetailsAuthenticationProvider.credentialsExpired", authException.getMessage());
			result.setMsg(msg);
		}

		if (authException instanceof UsernameNotFoundException) {
			String msg = SecurityMessageSource.getAccessor().getMessage(
					"AbstractUserDetailsAuthenticationProvider.noopBindAccount", authException.getMessage());
			result.setMsg(msg);
		}

		if (authException instanceof BadCredentialsException) {
			String msg = SecurityMessageSource.getAccessor().getMessage(
					"AbstractUserDetailsAuthenticationProvider.badClientCredentials", authException.getMessage());
			result.setMsg(msg);
		}

		response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
		PrintWriter printWriter = response.getWriter();
		printWriter.append(jsonMapper.writeValueAsString(result));
	}

}
