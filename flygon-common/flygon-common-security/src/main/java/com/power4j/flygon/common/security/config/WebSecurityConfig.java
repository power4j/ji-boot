package com.power4j.flygon.common.security.config;

import cn.hutool.core.util.StrUtil;
import com.power4j.flygon.common.security.auth.ApiTokenAuthenticationEntryPoint;
import com.power4j.flygon.common.security.auth.ApiTokenAuthenticationProvider;
import com.power4j.flygon.common.security.endpoint.ApiTokenEndpoint;
import com.power4j.flygon.common.security.filter.ApiTokenAuthenticationFilter;
import com.power4j.flygon.common.security.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/22
 * @since 1.0
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SecurityProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${flygon.token.endpoint.path:/token}")
	private String tokenEndpointPath;

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenService tokenService;

	@Bean
	@ConditionalOnMissingBean
	public ApiTokenEndpoint apiTokenEndpoint() throws Exception {
		return new ApiTokenEndpoint(tokenService, authenticationManager());
	}

	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ApiTokenAuthenticationProvider apiTokenAuthenticationProvider() {
		ApiTokenAuthenticationProvider apiTokenAuthenticationProvider = new ApiTokenAuthenticationProvider(tokenService,
				userDetailsService);
		return apiTokenAuthenticationProvider;
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/static/**","/webjars/**");
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
		applyMatchers(http, securityProperties.getExpose());
		http.headers().frameOptions().disable().and().authorizeRequests()
				.mvcMatchers(HttpMethod.POST, tokenEndpointPath).permitAll()
				.mvcMatchers(HttpMethod.DELETE, tokenEndpointPath + "/*").permitAll().anyRequest().authenticated().and()
				.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
		ApiTokenAuthenticationFilter apiTokenAuthenticationFilter = new ApiTokenAuthenticationFilter();
		http.authenticationProvider(apiTokenAuthenticationProvider()).addFilterAfter(apiTokenAuthenticationFilter,
				UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling().authenticationEntryPoint(new ApiTokenAuthenticationEntryPoint());
	}

	protected void applyMatchers(HttpSecurity http, Collection<SecurityProperties.MvcMatcher> mvcMatchers)
			throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
				.authorizeRequests();
		if (mvcMatchers != null && !mvcMatchers.isEmpty()) {
			for (SecurityProperties.MvcMatcher mvcMatcher : mvcMatchers) {
				if (mvcMatcher.getMethods() != null && !mvcMatcher.getMethods().isEmpty()) {
					Set<HttpMethod> methodSet = mvcMatcher.getMethods().stream()
							.map(m -> (m == null || m.trim().isEmpty()) ? null : HttpMethod.valueOf(m))
							.filter(m -> m != null).collect(Collectors.toSet());
					for (HttpMethod m : methodSet) {
						log.info("add matchers:[{}] {}", m.name(), StrUtil.join(", ",mvcMatcher.getPatterns()));
						registry.antMatchers(m, mvcMatcher.getPatterns().toArray(new String[0])).permitAll();
					}
				}
				else {
					log.info("add matchers:[{}] {}", "*", StrUtil.join(", ",mvcMatcher.getPatterns()));
					registry.antMatchers(mvcMatcher.getPatterns().toArray(new String[0])).permitAll();
				}
			}
		}
	}

}
