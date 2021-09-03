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

import cn.hutool.core.text.CharSequenceUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/9/1
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class ExternalUrlAuthorizationConfigurer
		implements ExpressionUrlAuthorizationConfigurerCustomizer<HttpSecurity> {

	private final SecureAccessProperties secureAccessProperties;

	@SneakyThrows
	@Override
	public void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity> configurer) {
		if (secureAccessProperties.isEnabled()) {
			applyHttpAccess(configurer.getRegistry(), secureAccessProperties.getFilters(),
					secureAccessProperties.getDefaultAccess());
		}
	}

	protected void applyHttpAccess(
			ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry,
			Collection<SecureAccessProperties.HttpAccess> httpAccesses, String defaultAccess) {
		if (httpAccesses != null && !httpAccesses.isEmpty()) {
			httpAccesses.forEach(httpAccess -> {
				Assert.hasText(httpAccess.getAccess(), "Access expression is required");
				if (httpAccess.getMethods() != null && !httpAccess.getMethods().isEmpty()) {
					Set<HttpMethod> methodSet = httpAccess.getMethods().stream()
							.map(m -> (m == null || m.trim().isEmpty()) ? null : HttpMethod.valueOf(m))
							.filter(m -> m != null).collect(Collectors.toSet());
					for (HttpMethod m : methodSet) {
						log.info("Add access {} : [{}] {}", httpAccess.getAccess(), m.name(),
								CharSequenceUtil.join(", ", httpAccess.getPatterns()));
						registry.antMatchers(m, httpAccess.getPatterns().toArray(new String[0]))
								.access(httpAccess.getAccess());
					}
				}
				else {
					log.info("add access {} : [{}] {}", httpAccess.getAccess(), "*",
							CharSequenceUtil.join(", ", httpAccess.getPatterns()));
					registry.antMatchers(httpAccess.getPatterns().toArray(new String[0]))
							.access(httpAccess.getAccess());
				}
			});
		}
		log.info("default access : {}", defaultAccess);
		registry.anyRequest().access(defaultAccess);
	}

}
