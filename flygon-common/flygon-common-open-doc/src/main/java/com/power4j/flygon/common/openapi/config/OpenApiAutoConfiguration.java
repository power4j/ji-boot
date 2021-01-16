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

package com.power4j.flygon.common.openapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger3 config
 * <p>
 *
 * @see <a href="https://springdoc.org/faq.html">springdoc</a>
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-18
 * @since 1.0
 */
@Configuration(proxyBeanMethods = false)
public class OpenApiAutoConfiguration {

	@Value("${spring.application.name:demo}")
	private String applicationName;

	@Value("${springdoc.version:1.0}")
	private String applicationVersion;

	@Bean
	public OpenAPI openAPI() {
		OpenAPI openAPI = new OpenAPI().info(new Info().title(applicationName).version(applicationVersion));
		return openAPI;
	}

}
