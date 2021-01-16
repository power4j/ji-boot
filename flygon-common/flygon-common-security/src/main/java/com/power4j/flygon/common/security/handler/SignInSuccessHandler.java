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

package com.power4j.flygon.common.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import com.power4j.flygon.common.core.util.HttpServletResponseUtil;
import com.power4j.flygon.common.security.model.ApiToken;
import com.power4j.flygon.common.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

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
@RequiredArgsConstructor
public class SignInSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Setter
	private ObjectMapper objectMapper = new ObjectMapper();

	private final TokenService tokenService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.debug("Authentication success : {}", authentication.getName());
		ApiToken token = tokenService.createToken(authentication);
		HttpServletResponseUtil.writeJson(objectMapper, response, ApiResponseUtil.ok(token), HttpStatus.OK);

		clearAuthenticationAttributes(request);
	}

}
