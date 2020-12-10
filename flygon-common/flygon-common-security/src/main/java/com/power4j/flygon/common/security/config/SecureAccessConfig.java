package com.power4j.flygon.common.security.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 简化WebSecurity配置
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/26
 * @since 1.0
 */
@Slf4j
@Order(1000)
@Configuration
@AutoConfigureBefore(WebSecurityConfig.class)
@EnableConfigurationProperties(SecureAccessProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = SecureAccessProperties.PREFIX,name = "enabled",havingValue = "true",matchIfMissing = true)
public class SecureAccessConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecureAccessProperties secureAccessProperties;

	@Override
	public void configure(WebSecurity web) {
		applyWebSecurity(web,secureAccessProperties.getIgnore().patterns);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		applyHttpAccess(http, secureAccessProperties.httpAccesses);
	}

	protected void applyWebSecurity(WebSecurity web, Collection<String> patterns) {
		patterns.forEach(pattern -> {
			log.info("config web security, ignoring: {}", pattern);
			web.ignoring().antMatchers(pattern);
		});
	}

	protected void applyHttpAccess(HttpSecurity http, Collection<SecureAccessProperties.HttpAccess> httpAccesses)
			throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
				.authorizeRequests();
		if (httpAccesses != null && !httpAccesses.isEmpty()) {
			httpAccesses.forEach(httpAccess -> {
				Assert.hasText(httpAccess.getAccess(),"access expression is required");
				if (httpAccess.getMethods() != null && !httpAccess.getMethods().isEmpty()) {
					Set<HttpMethod> methodSet = httpAccess.getMethods().stream()
							.map(m -> (m == null || m.trim().isEmpty()) ? null : HttpMethod.valueOf(m))
							.filter(m -> m != null).collect(Collectors.toSet());
					for (HttpMethod m : methodSet) {
						log.info("add access {} : [{}] {}", httpAccess.getAccess(),m.name(), StrUtil.join(", ", httpAccess.getPatterns()));
						registry.antMatchers(m, httpAccess.getPatterns().toArray(new String[0])).access(httpAccess.getAccess());
					}
				}
				else {
					log.info("add access {} : [{}] {}", httpAccess.getAccess(),"*", StrUtil.join(", ", httpAccess.getPatterns()));
					registry.antMatchers(httpAccess.getPatterns().toArray(new String[0])).access(httpAccess.getAccess());
				}
			});
		}
	}
}
