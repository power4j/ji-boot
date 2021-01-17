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

package com.power4j.ji.common.openapi.config;

import com.power4j.ji.common.core.constant.CommonConstant;
import com.power4j.ji.common.core.constant.SecurityConstant;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/25
 * @since 1.0
 */
@Slf4j
@ConditionalOnProperty(prefix = CommonConstant.PROPERTY_PREFIX + ".doc.global-security-scheme", name = "enabled",
		havingValue = "true")
public class SecuritySchemePostProcessor implements BeanPostProcessor {

	private final static String SCHEMA_NAME = "api-token";

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof OpenAPI) {
			OpenAPI openAPI = (OpenAPI) bean;
			Map<String, SecurityScheme> securitySchemeMap = Optional.ofNullable(openAPI.getComponents())
					.map(components -> components.getSecuritySchemes()).orElse(Collections.emptyMap());

			if (securitySchemeMap.isEmpty()) {
				log.info("Add SecuritySchemes for : {}", Optional.ofNullable(openAPI.getInfo()).map(Info::getTitle));
				openAPI.components(new Components().addSecuritySchemes(SCHEMA_NAME,
						new SecurityScheme().type(SecurityScheme.Type.APIKEY).name(SecurityConstant.HEADER_TOKEN_KEY)
								.in(SecurityScheme.In.HEADER)))
						.security(Arrays.asList(new SecurityRequirement().addList(SCHEMA_NAME)));
			}
			else {
				log.warn("Skip add SecuritySchemes for : {}",
						Optional.ofNullable(openAPI.getInfo()).map(Info::getTitle).orElse(null));
			}
		}
		return bean;
	}

}
