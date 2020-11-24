package com.power4j.flygon.common.security.config;

import com.power4j.flygon.common.security.auth.ApiTokenAuthenticationEntryPoint;
import com.power4j.flygon.common.security.auth.ApiTokenAuthenticationProvider;
import com.power4j.flygon.common.security.endpoint.ApiTokenEndpoint;
import com.power4j.flygon.common.security.filter.ApiTokenAuthenticationFilter;
import com.power4j.flygon.common.security.service.TokenService;
import com.power4j.flygon.common.security.service.impl.DemoTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/22
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${flygon.token.endpoint.path:/token}")
	private String tokenEndpointPath;

	@Autowired
	private UserDetailsService userDetailsService;


	@Bean
	@ConditionalOnMissingBean
	public TokenService tokenService(){
		return new DemoTokenService();
	}

	@Bean
	@ConditionalOnMissingBean
	public ApiTokenEndpoint apiTokenEndpoint() throws Exception {
		return new ApiTokenEndpoint(tokenService(),authenticationManager());
	}

	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ApiTokenAuthenticationProvider apiTokenAuthenticationProvider(){
		ApiTokenAuthenticationProvider apiTokenAuthenticationProvider = new ApiTokenAuthenticationProvider(tokenService());
		return apiTokenAuthenticationProvider;
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/css/**");
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
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.authorizeRequests().mvcMatchers(HttpMethod.POST,tokenEndpointPath).permitAll();
		http.authorizeRequests().mvcMatchers(HttpMethod.DELETE,tokenEndpointPath+"/*").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		http.authorizeRequests()
				.and()
				.csrf()
				.disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.NEVER);

		ApiTokenAuthenticationFilter apiTokenAuthenticationFilter = new ApiTokenAuthenticationFilter();
		http.authenticationProvider(apiTokenAuthenticationProvider()).addFilterAfter(apiTokenAuthenticationFilter,
				UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling().authenticationEntryPoint(new ApiTokenAuthenticationEntryPoint());
	}
}

