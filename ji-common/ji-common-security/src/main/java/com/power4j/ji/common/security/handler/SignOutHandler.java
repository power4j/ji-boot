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
import com.power4j.ji.common.security.service.TokenService;
import com.power4j.ji.common.security.util.ApiTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/27
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class SignOutHandler implements LogoutHandler {

	@Setter
	private ObjectMapper objectMapper = new ObjectMapper();

	private final TokenService tokenService;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		log.debug("Handling logout: {}", Optional.ofNullable(authentication).map(o -> o.getName()).orElse(null));
		try {
			doLogout(request, response);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	protected void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ApiResponse<?> data = ApiResponseUtil.ok();
		String tokenValue = ApiTokenUtil.getApiTokenValue(request);
		if (tokenValue == null || tokenValue.isEmpty()) {
			data = ApiResponseUtil.badRequest("No token");
		}
		else {
			if (!tokenService.deleteToken(tokenValue)) {
				data = ApiResponseUtil.badRequest("Bad token : " + tokenValue);
			}
		}
		HttpServletResponseUtil.writeJson(objectMapper, response, data, HttpStatus.OK);
	}

}
