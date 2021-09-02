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

package com.power4j.ji.common.security.web;

import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.security.msg.SecurityMessageSource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/9/2
 * @since 1.0
 */
@Slf4j
public abstract class AbstractAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Getter
	private final MessageSourceAccessor messages = SecurityMessageSource.getAccessor();


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException {
		final String reqUrl = request.getRequestURI();
		log.debug("Handling {} : {} {}", authException.getClass().getSimpleName(), authException.getMessage(), reqUrl);
		renderResponse(request,response,authException);
	}

	/**
	 * 写响应信息
	 * @param request
	 * @param response
	 * @param authException
	 * @throws IOException
	 */
	protected abstract void renderResponse(HttpServletRequest request, HttpServletResponse response,
										   AuthenticationException authException) throws IOException;


	protected int determineApiResponseCode(AuthenticationException authException){
		return SysErrorCodes.E_FAIL;
	}

	protected ApiResponse<?> makeApiResponse(AuthenticationException authException){
		ApiResponse<Object> result = ApiResponse.of(determineApiResponseCode(authException), authException.getMessage());

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

		return result;
	}
}
