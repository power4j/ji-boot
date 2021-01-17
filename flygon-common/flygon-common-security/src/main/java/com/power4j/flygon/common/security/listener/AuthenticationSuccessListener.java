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

package com.power4j.flygon.common.security.listener;

import com.power4j.flygon.common.core.constant.CommonConstant;
import com.power4j.flygon.common.core.context.RequestContext;
import com.power4j.flygon.common.security.LoginUser;
import com.power4j.flygon.common.security.model.ApiToken;
import com.power4j.flygon.common.security.token.ApiTokenAuthentication;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/17
 * @since 1.0
 */
@RequiredArgsConstructor
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

	private final RequestContext requestContext;

	@Autowired
	private ObjectProvider<AuthenticationSuccessHandler> authenticationSuccessHandlerObjectProvider;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		Authentication authentication = event.getAuthentication();
		updateContext(authentication);
		authenticationSuccessHandlerObjectProvider.stream().forEach(h -> h.handleAuthenticationSuccess(authentication));
	}

	private void updateContext(Authentication authentication) {
		String uid = null;
		if (authentication instanceof LoginUser) {
			uid = ((LoginUser) authentication).getUid().toString();
		}
		else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
			uid = Optional.ofNullable(authentication.getDetails()).filter(o -> o instanceof ApiToken)
					.map(o -> ((ApiToken) o).getUuid().toString()).orElse(null);
		}
		if (uid != null) {
			requestContext.setAccountId(uid);
			MDC.put(CommonConstant.MDC_ACCOUNT_ID, uid);
		}
	}

}
