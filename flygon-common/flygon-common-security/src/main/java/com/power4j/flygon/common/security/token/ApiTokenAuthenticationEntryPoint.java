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

package com.power4j.flygon.common.security.token;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import com.power4j.flygon.common.security.msg.SecurityMessageSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
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
@RequiredArgsConstructor
public class ApiTokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper jsonMapper;

	protected final MessageSourceAccessor messages = SecurityMessageSource.getAccessor();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		final String reqUrl = request.getRequestURI();
		log.debug("Handling {} : {} {}", authException.getClass().getSimpleName(), authException.getMessage(), reqUrl);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ApiResponse<Object> result = ApiResponseUtil.fail(authException.getMessage());
		result.setData(reqUrl);

		if (authException instanceof InsufficientAuthenticationException) {
			String msg = messages.getMessage("AbstractAccessDecisionManager.accessDenied", authException.getMessage());
			result.setMsg(msg);
		}

		if (authException instanceof CredentialsExpiredException) {
			String msg = messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired",
					authException.getMessage());
			result.setMsg(msg);
		}

		if (authException instanceof UsernameNotFoundException) {
			String msg = messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
					authException.getMessage());
			result.setMsg(msg);
		}

		if (authException instanceof BadCredentialsException) {
			String msg = SecurityMessageSource.getAccessor()
					.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", authException.getMessage());
			result.setMsg(msg);
		}

		response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
		PrintWriter printWriter = response.getWriter();
		printWriter.println(jsonMapper.writeValueAsString(result));
	}

}
