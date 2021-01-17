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

import com.power4j.ji.common.core.constant.SecurityConstant;
import com.power4j.ji.common.security.util.ApiTokenUtil;
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
			String tokenValue = ApiTokenUtil.getApiTokenValue(request);
			if (tokenValue != null && !tokenValue.isEmpty()) {
				ApiTokenAuthentication apiTokenAuthentication = new ApiTokenAuthentication(tokenValue.trim());
				SecurityContextHolder.getContext().setAuthentication(apiTokenAuthentication);
			}
			request.setAttribute(SecurityConstant.TOKEN_ATTRIBUTE_KEY, true);
		}
		filterChain.doFilter(request, response);
	}

}
