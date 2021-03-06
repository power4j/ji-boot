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

import cn.hutool.core.text.CharSequenceUtil;
import com.power4j.ji.common.security.model.ApiToken;
import com.power4j.ji.common.security.msg.SecurityMessageSource;
import com.power4j.ji.common.security.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.time.LocalDateTime;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/22
 * @since 1.0
 */
@Slf4j
@AllArgsConstructor
public class ApiTokenAuthenticationProvider implements AuthenticationProvider {

	protected final MessageSourceAccessor messages = SecurityMessageSource.getAccessor();

	private final TokenService tokenService;

	private final UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (authentication.isAuthenticated()) {
			return authentication;
		}
		String tokenValue = authentication.getCredentials().toString();
		if (CharSequenceUtil.isBlank(tokenValue)) {
			log.debug("认证失败:token为空");
			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "无效的凭据"));
		}
		ApiToken apiToken = tokenService.loadApiToken(tokenValue);
		if (apiToken == null) {
			log.debug("认证失败:无效的token");
			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "无效的凭据"));
		}
		if (apiToken.getExpireIn().isBefore(LocalDateTime.now())) {
			log.debug("认证失败:token已经过期");
			throw new CredentialsExpiredException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", "凭据已经过期"));
		}
		UserDetails userDetails = userDetailsService.loadUserByUsername(apiToken.getUsername());
		PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(
				userDetails, tokenValue, userDetails.getAuthorities());
		preAuthenticatedAuthenticationToken.setDetails(apiToken);
		preAuthenticatedAuthenticationToken.setAuthenticated(true);
		return preAuthenticatedAuthenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return ApiTokenAuthentication.class.isAssignableFrom(authentication);
	}

}
