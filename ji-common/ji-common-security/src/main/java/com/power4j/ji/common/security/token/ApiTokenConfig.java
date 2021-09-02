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

import com.power4j.ji.common.security.config.ExpressionUrlAuthorizationConfigurerCustomizer;
import com.power4j.ji.common.security.config.SecurityProperties;
import com.power4j.ji.common.security.config.WebSecurityConfig;
import com.power4j.ji.common.security.handler.DefaultLogoutHandler;
import com.power4j.ji.common.security.handler.DefaultLogoutSuccessHandler;
import com.power4j.ji.common.security.service.TokenService;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/9/2
 * @since 1.0
 */
@Slf4j
@Order(500)
@Configuration
@RequiredArgsConstructor
@AutoConfigureBefore(WebSecurityConfig.class)
@ConditionalOnBean({SocialLoginHandler.class, SocialUserDetailsService.class})
@EnableConfigurationProperties({SecurityProperties.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ApiTokenConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private ObjectProvider<UserDetailsService> userDetailsServiceObjectProvider;

	@Autowired
	private ObjectProvider<ExpressionUrlAuthorizationConfigurerCustomizer<HttpSecurity>> authorizationConfigurerCustomizers;

	@Autowired
	private ObjectProvider<TokenService> tokenServiceObjectProvider;


	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsServiceObjectProvider.getObject());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		auth.authenticationProvider(daoAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		DefaultLogoutHandler defaultLogoutHandler = new DefaultLogoutHandler(tokenServiceObjectProvider.getObject());

		// @formatter:off
		http.mvcMatcher(securityProperties.getLoginUrl())
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
				.logout()
				.logoutRequestMatcher(
						new AntPathRequestMatcher(securityProperties.getLogoutUrl(), HttpMethod.POST.name()))
				.addLogoutHandler(defaultLogoutHandler).logoutSuccessHandler(new DefaultLogoutSuccessHandler()).permitAll()
				.and()
				.authorizeRequests()
				.mvcMatchers(HttpMethod.POST, securityProperties.getLoginUrl(), securityProperties.getLogoutUrl())
				.permitAll();
		// @formatter:on
	}

	protected GenerateApiTokenFilter loginFilter() throws Exception {
		LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler(tokenServiceObjectProvider.getObject());
		LoginFailureHandler loginFailureHandler = new LoginFailureHandler();

		GenerateApiTokenFilter filter = new GenerateApiTokenFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setFilterProcessesUrl(securityProperties.getLoginUrl());
		filter.setAuthenticationSuccessHandler(loginSuccessHandler);
		filter.setAuthenticationFailureHandler(loginFailureHandler);
		return filter;
	}
}
