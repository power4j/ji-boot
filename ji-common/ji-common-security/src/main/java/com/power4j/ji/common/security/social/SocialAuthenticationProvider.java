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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/8/26
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class SocialAuthenticationProvider implements AuthenticationProvider {

	private final UserDetailsChecker userDetailsChecker = new DefaultUserDetailsChecker();

	private final SocialUserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.isInstanceOf(SocialAuthenticationToken.class, authentication, "Not SocialAuthenticationToken");

		final String key = authentication.getPrincipal().toString();
		final String state = authentication.getCredentials().toString();
		UserDetails userDetails;
		try {
			userDetails = userDetailsService.loadBySocial(key, state);
		}
		catch (UsernameNotFoundException e) {
			log.warn(e.getMessage());
			throw e;
		}
		if (userDetails == null) {
			// TODO: 更加明确的错误提示？
			throw new UsernameNotFoundException("该用户未注册");
		}
		userDetailsChecker.check(userDetails);
		return new SocialAuthenticationToken(userDetails, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return SocialAuthenticationToken.class.equals(aClass);
	}

	private static class DefaultUserDetailsChecker implements UserDetailsChecker {

		@Override
		public void check(UserDetails user) {
			if (!user.isAccountNonLocked()) {
				throw new LockedException("User account is locked");
			}
			if (!user.isEnabled()) {
				throw new DisabledException("User is disabled");
			}
			if (!user.isAccountNonExpired()) {
				throw new AccountExpiredException("User account has expired");
			}
			if (!user.isCredentialsNonExpired()) {
				throw new CredentialsExpiredException("User credentials have expired");
			}
		}

	}

}
