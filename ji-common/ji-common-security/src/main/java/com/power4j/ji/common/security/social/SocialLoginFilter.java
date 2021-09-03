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

package com.power4j.ji.common.security.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/8/26
 * @since 1.0
 */
public class SocialLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final ObjectMapper objectMapper = new ObjectMapper();

	public SocialLoginFilter(String defaultFilterProcessesUrl) {
		super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
		if (!HttpMethod.POST.name().equalsIgnoreCase(httpServletRequest.getMethod())) {
			throw new AuthenticationServiceException(
					String.format("Authentication method not supported: %s,Use %s instead", HttpMethod.POST.name(),
							HttpMethod.POST.name()));
		}
		SocialAuthenticationToken socialAuthenticationToken = getSocialAuthenticationToken(httpServletRequest);
		// Allow subclasses to set the "details" property
		setDetails(httpServletRequest, socialAuthenticationToken);
		return this.getAuthenticationManager().authenticate(socialAuthenticationToken);
	}

	protected void setDetails(HttpServletRequest request, SocialAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}

	protected SocialAuthenticationToken getSocialAuthenticationToken(HttpServletRequest request) {

		CodeLoginRequest loginRequest;
		try (Reader reader = request.getReader()) {
			loginRequest = objectMapper.readValue(reader, CodeLoginRequest.class);
		}
		catch (IOException e) {
			throw new AuthenticationServiceException("failed to get login body from request", e);
		}
		return new SocialAuthenticationToken(loginRequest.getType(), loginRequest.getCode());
	}

}
