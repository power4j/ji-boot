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

package com.power4j.ji.common.security.config;

import com.power4j.ji.common.security.service.TokenService;
import com.power4j.ji.common.security.social.SocialAuthenticationProvider;
import com.power4j.ji.common.security.social.SocialLoginFilter;
import com.power4j.ji.common.security.social.SocialLoginHandler;
import com.power4j.ji.common.security.social.SocialUserDetailsService;
import com.power4j.ji.common.security.web.LoginFailureHandler;
import com.power4j.ji.common.security.web.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/8/26
 * @since 1.0
 */
@Slf4j
@Order(1000)
@Configuration
@RequiredArgsConstructor
@AutoConfigureBefore(WebSecurityConfig.class)
@ConditionalOnBean({SocialLoginHandler.class, SocialUserDetailsService.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SocialLoginConfig extends WebSecurityConfigurerAdapter {
	private final SecurityProperties securityProperties;
	private final ObjectProvider<SocialUserDetailsService> userDetailsServiceObjectProvider;
	private TokenService tokenService;

	@Autowired
	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	protected SocialLoginFilter socialLoginFilter() throws Exception {
		SocialLoginFilter filter = new SocialLoginFilter(securityProperties.getSocialLoginUrl());
		filter.setAuthenticationManager(authenticationManager());

		LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler(tokenService);
		LoginFailureHandler loginFailureHandler = new LoginFailureHandler();
		filter.setAuthenticationFailureHandler(loginFailureHandler);
		filter.setAuthenticationSuccessHandler(loginSuccessHandler);
		return filter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.mvcMatcher(securityProperties.getSocialLoginUrl())
				.csrf()
				.disable()
				.addFilterAfter(socialLoginFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().mvcMatchers(HttpMethod.POST,securityProperties.getSocialLoginUrl()).permitAll().and()
				.authenticationProvider(createSocialAuthenticationProvider());
		// @formatter:on
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth){
		SocialUserDetailsService socialUserDetailsService = userDetailsServiceObjectProvider.getObject();
		SocialAuthenticationProvider provider = new SocialAuthenticationProvider(socialUserDetailsService);
		auth.authenticationProvider(provider);
	}

	public SocialAuthenticationProvider createSocialAuthenticationProvider(){
		return new SocialAuthenticationProvider(userDetailsServiceObjectProvider.getObject());
	}
}
