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

package com.power4j.ji.common.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.ji.common.core.util.HttpServletResponseUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/27
 * @since 1.0
 */
@Slf4j
public class SignInFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Setter
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.debug("Authentication failure {} : {}", exception.getClass().getSimpleName(),
				exception.getLocalizedMessage());
		ApiResponse<?> data;

		if (exception.getClass().equals(CredentialsExpiredException.class)) {
			data = ApiResponseUtil.fail("凭据已经过期");
		}
		else if (exception.getClass().equals(AccountExpiredException.class)) {
			data = ApiResponseUtil.fail("账号已经过期");
		}
		else if (exception.getClass().equals(DisabledException.class)) {
			data = ApiResponseUtil.fail("账号已禁用");
		}
		else if (exception.getClass().equals(LockedException.class)) {
			data = ApiResponseUtil.fail("账号已锁定");
		}
		else if (exception.getClass().equals(BadCredentialsException.class)) {
			data = ApiResponseUtil.fail("用户名或密码错误");
		}
		else {
			data = ApiResponseUtil.fail("认证失败:" + exception.getLocalizedMessage());
		}
		HttpServletResponseUtil.writeJson(objectMapper, response, data, HttpStatus.OK);
	}

}
