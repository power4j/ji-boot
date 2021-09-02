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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.power4j.ji.common.core.context.RequestContext;
import com.power4j.ji.common.security.handler.DefaultLogoutHandler;
import com.power4j.ji.common.security.handler.DefaultLogoutSuccessHandler;
import com.power4j.ji.common.security.listener.AuthenticationFailureListener;
import com.power4j.ji.common.security.listener.AuthenticationSuccessListener;
import com.power4j.ji.common.security.service.PermissionService;
import com.power4j.ji.common.security.service.TokenService;
import com.power4j.ji.common.security.token.GenerateApiTokenFilter;
import com.power4j.ji.common.security.token.ApiTokenAuthenticationConverter;
import com.power4j.ji.common.security.token.ApiTokenAuthenticationFilter;
import com.power4j.ji.common.security.token.ApiTokenAuthenticationProvider;
import com.power4j.ji.common.security.web.AccessDeniedEntryPoint;
import com.power4j.ji.common.security.web.LoginFailureHandler;
import com.power4j.ji.common.security.web.LoginSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The main web security config
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/22
 * @since 1.0
 */
@Slf4j
@Order
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
	private ObjectProvider<UserDetailsService> userDetailsServiceObjectProvider;

	@Autowired
	private ObjectProvider<ExpressionUrlAuthorizationConfigurerCustomizer<HttpSecurity>> authorizationConfigurerCustomizers;

	@Autowired
	private TokenService tokenService;

	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean("pms")
	public PermissionService permissionService() {
		return new PermissionService();
	}

	@Bean
	public ApiTokenAuthenticationProvider apiTokenAuthenticationProvider() {
		return new ApiTokenAuthenticationProvider(tokenService,
				userDetailsServiceObjectProvider.getObject());
	}

	protected GenerateApiTokenFilter loginFilter() throws Exception {
		LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler(tokenService);
		LoginFailureHandler loginFailureHandler = new LoginFailureHandler();

		GenerateApiTokenFilter filter = new GenerateApiTokenFilter();
		filter.setObjectMapper(objectMapper);
		filter.setAuthenticationManager(authenticationManager());
		filter.setFilterProcessesUrl(securityProperties.getLoginUrl());
		filter.setAuthenticationSuccessHandler(loginSuccessHandler);
		filter.setAuthenticationFailureHandler(loginFailureHandler);
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

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer(){

		return (webSecurity) -> {
			if (secureAccessProperties.isEnabled()) {
				secureAccessProperties.getIgnore().getPatterns().forEach(pattern -> {
					log.info("Config web security, ignoring: {}", pattern);
					webSecurity.ignoring().antMatchers(pattern);
				});
			}
		};
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(apiTokenAuthenticationProvider());
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsServiceObjectProvider.getObject());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		auth.authenticationProvider(daoAuthenticationProvider);
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		DefaultLogoutHandler defaultLogoutHandler = new DefaultLogoutHandler(tokenService);
		defaultLogoutHandler.setObjectMapper(objectMapper);

		// @formatter:off
		http.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(createApiTokenAuthenticationFilter(), BasicAuthenticationFilter.class)
				.exceptionHandling().authenticationEntryPoint(new AccessDeniedEntryPoint())
				.and()
				.headers().frameOptions().disable()
				.and()
				.logout()
				.logoutRequestMatcher(
						new AntPathRequestMatcher(securityProperties.getLogoutUrl(), HttpMethod.POST.name()))
				.addLogoutHandler(defaultLogoutHandler).logoutSuccessHandler(new DefaultLogoutSuccessHandler()).permitAll()
				.and()
				.authorizeRequests()
				.mvcMatchers(HttpMethod.POST, securityProperties.getLoginUrl(), securityProperties.getLogoutUrl())
				.permitAll();

		ExpressionUrlAuthorizationConfigurer<HttpSecurity> authorizationConfigurer = (ExpressionUrlAuthorizationConfigurer<HttpSecurity>)http.getConfigurer(ExpressionUrlAuthorizationConfigurer.class);
		if(authorizationConfigurer == null){
			authorizationConfigurer = new ExpressionUrlAuthorizationConfigurer<>(http.getSharedObject(ApplicationContext.class));
		}
		applyAuthorizationConfigurerCustomizers(authorizationConfigurer);
		// @formatter:on
	}

	protected void applyAuthorizationConfigurerCustomizers(ExpressionUrlAuthorizationConfigurer<HttpSecurity> configurer) {
		List<ExpressionUrlAuthorizationConfigurerCustomizer<HttpSecurity>> customizers = authorizationConfigurerCustomizers.orderedStream().collect(Collectors.toList());
		customizers.forEach(o -> o.customize(configurer));
		getDefaultUrlRegistryCustomizer().customize(configurer);
	}

	protected ApiTokenAuthenticationFilter createApiTokenAuthenticationFilter() throws Exception {
		ApiTokenAuthenticationFilter authenticationFilter = new ApiTokenAuthenticationFilter(authenticationManagerBean(),new ApiTokenAuthenticationConverter());
		AccessDeniedEntryPoint entryPoint = new AccessDeniedEntryPoint();
		authenticationFilter.setFailureHandler(entryPoint::commence);
		authenticationFilter.setSuccessHandler((request, response, authentication) -> {} );
		return authenticationFilter;
	}

	protected ExternalUrlAuthorizationConfigurer getDefaultUrlRegistryCustomizer(){
		return new ExternalUrlAuthorizationConfigurer(secureAccessProperties);
	}
}
