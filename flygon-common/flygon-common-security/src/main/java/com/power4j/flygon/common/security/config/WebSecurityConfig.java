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

package com.power4j.flygon.common.security.config;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.power4j.flygon.common.core.context.RequestContext;
import com.power4j.flygon.common.security.filter.SignInFilter;
import com.power4j.flygon.common.security.handler.SignInFailureHandler;
import com.power4j.flygon.common.security.handler.SignInSuccessHandler;
import com.power4j.flygon.common.security.handler.SignOutHandler;
import com.power4j.flygon.common.security.handler.SignOutSuccessHandler;
import com.power4j.flygon.common.security.listener.AuthenticationFailureListener;
import com.power4j.flygon.common.security.listener.AuthenticationSuccessListener;
import com.power4j.flygon.common.security.service.PermissionService;
import com.power4j.flygon.common.security.service.TokenService;
import com.power4j.flygon.common.security.token.ApiTokenAuthenticationEntryPoint;
import com.power4j.flygon.common.security.token.ApiTokenAuthenticationFilter;
import com.power4j.flygon.common.security.token.ApiTokenAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/22
 * @since 1.0
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableConfigurationProperties({ SecurityProperties.class, SecureAccessProperties.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private SecureAccessProperties secureAccessProperties;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenService tokenService;

	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean("pms")
	@ConditionalOnMissingBean
	public PermissionService permissionService() {
		return new PermissionService();
	}

	@Bean
	public ApiTokenAuthenticationProvider apiTokenAuthenticationProvider() {
		ApiTokenAuthenticationProvider apiTokenAuthenticationProvider = new ApiTokenAuthenticationProvider(tokenService,
				userDetailsService);
		return apiTokenAuthenticationProvider;
	}

	@Bean
	public SignInFilter signInFilter() throws Exception {
		SignInSuccessHandler signInSuccessHandler = new SignInSuccessHandler(tokenService);
		signInSuccessHandler.setObjectMapper(objectMapper);
		SignInFailureHandler signInFailureHandler = new SignInFailureHandler();
		signInFailureHandler.setObjectMapper(objectMapper);

		SignInFilter filter = new SignInFilter();
		filter.setObjectMapper(objectMapper);
		filter.setAuthenticationManager(authenticationManager());
		filter.setFilterProcessesUrl(securityProperties.getLoginUrl());
		filter.setAuthenticationSuccessHandler(signInSuccessHandler);
		filter.setAuthenticationFailureHandler(signInFailureHandler);
		return filter;
	}

	@Bean
	public AuthenticationSuccessListener authenticationSuccessListener(RequestContext requestContext) {
		return new AuthenticationSuccessListener(requestContext);
	}

	@Bean
	public AuthenticationFailureListener authenticationFailureListener() {
		return new AuthenticationFailureListener();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(apiTokenAuthenticationProvider());
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		auth.authenticationProvider(daoAuthenticationProvider);
	}

	@Override
	public void configure(WebSecurity web) {
		if (secureAccessProperties.isEnabled()) {
			applyWebSecurity(web, secureAccessProperties.getIgnore().getPatterns());
		}
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		ApiTokenAuthenticationFilter apiTokenAuthenticationFilter = new ApiTokenAuthenticationFilter();
		http.authenticationProvider(apiTokenAuthenticationProvider());
		http.addFilterBefore(signInFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterAfter(apiTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling().authenticationEntryPoint(new ApiTokenAuthenticationEntryPoint(objectMapper));
		http.headers().frameOptions().disable();
		http.authorizeRequests()
				.mvcMatchers(HttpMethod.POST, securityProperties.getLoginUrl(), securityProperties.getLogoutUrl())
				.permitAll();

		SignOutHandler signOutHandler = new SignOutHandler(tokenService);
		signOutHandler.setObjectMapper(objectMapper);
		http.logout()
				.logoutRequestMatcher(
						new AntPathRequestMatcher(securityProperties.getLogoutUrl(), HttpMethod.POST.name()))
				.addLogoutHandler(signOutHandler).logoutSuccessHandler(new SignOutSuccessHandler()).permitAll();
		if (secureAccessProperties.isEnabled()) {
			applyHttpAccess(http, secureAccessProperties.getFilters());
		}
		http.authorizeRequests().anyRequest().authenticated();
	}

	protected void applyWebSecurity(WebSecurity web, Collection<String> patterns) {
		patterns.forEach(pattern -> {
			log.info("Config web security, ignoring: {}", pattern);
			web.ignoring().antMatchers(pattern);
		});
	}

	protected void applyHttpAccess(HttpSecurity http, Collection<SecureAccessProperties.HttpAccess> httpAccesses)
			throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
				.authorizeRequests();
		if (httpAccesses != null && !httpAccesses.isEmpty()) {
			httpAccesses.forEach(httpAccess -> {
				Assert.hasText(httpAccess.getAccess(), "Access expression is required");
				if (httpAccess.getMethods() != null && !httpAccess.getMethods().isEmpty()) {
					Set<HttpMethod> methodSet = httpAccess.getMethods().stream()
							.map(m -> (m == null || m.trim().isEmpty()) ? null : HttpMethod.valueOf(m))
							.filter(m -> m != null).collect(Collectors.toSet());
					for (HttpMethod m : methodSet) {
						log.info("Add access {} : [{}] {}", httpAccess.getAccess(), m.name(),
								StrUtil.join(", ", httpAccess.getPatterns()));
						registry.antMatchers(m, httpAccess.getPatterns().toArray(new String[0]))
								.access(httpAccess.getAccess());
					}
				}
				else {
					log.info("add access {} : [{}] {}", httpAccess.getAccess(), "*",
							StrUtil.join(", ", httpAccess.getPatterns()));
					registry.antMatchers(httpAccess.getPatterns().toArray(new String[0]))
							.access(httpAccess.getAccess());
				}
			});
		}
	}

}
