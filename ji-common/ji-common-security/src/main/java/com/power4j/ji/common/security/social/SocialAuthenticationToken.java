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

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/8/26
 * @since 1.0
 */
public class SocialAuthenticationToken extends AbstractAuthenticationToken {

	private final String code;

	private final Object principal;

	public SocialAuthenticationToken(String type, String code) {
		super(Collections.emptyList());
		this.principal = type;
		this.code = code;
	}

	public SocialAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.code = null;
		super.setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return code;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

}
