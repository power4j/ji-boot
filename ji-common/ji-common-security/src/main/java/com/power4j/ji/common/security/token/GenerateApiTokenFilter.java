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

package com.power4j.ji.common.security.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/27
 * @since 1.0
 */
@Slf4j
public class GenerateApiTokenFilter extends UsernamePasswordAuthenticationFilter {

	@Setter
	private boolean postOnly = true;

	@Getter
	@Setter
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		if (postOnly && !HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
			throw new AuthenticationServiceException(
					String.format("Authentication method not supported: %s,Use %s instead", request.getMethod(),
							HttpMethod.POST.name()));
		}
		UsernameLoginRequest usernameLoginRequest;
		MediaType mediaType = null;
		try {
			mediaType = MediaType.parseMediaType(request.getHeader(HttpHeaders.CONTENT_TYPE));
		}
		catch (InvalidMediaTypeException e) {
			log.warn(e.getMessage(), e);
		}
		if (mediaType != null && mediaType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
			usernameLoginRequest = getAuthInfoFromBody(request);
		}
		else {
			usernameLoginRequest = getAuthInfoFromFormData(request);
		}
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				usernameLoginRequest.getUsername(), usernameLoginRequest.getPassword());
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	@Override
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}

	private UsernameLoginRequest getAuthInfoFromBody(HttpServletRequest request) {
		try (Reader reader = request.getReader()) {
			return objectMapper.readValue(reader, UsernameLoginRequest.class);
		}
		catch (IOException e) {
			throw new AuthenticationServiceException("failed to get username or password from request", e);
		}
	}

	private UsernameLoginRequest getAuthInfoFromFormData(HttpServletRequest request) {
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		UsernameLoginRequest usernameLoginRequest = new UsernameLoginRequest();
		usernameLoginRequest.setUsername(username);
		usernameLoginRequest.setPassword(password);
		return usernameLoginRequest;
	}

}
