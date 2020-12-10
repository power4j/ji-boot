package com.power4j.flygon.common.openapi.config;

import com.power4j.flygon.common.core.constant.CommonConstant;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/25
 * @since 1.0
 */
@Configuration
@ConditionalOnBean(OpenAPI.class)
@AutoConfigureAfter(OpenApiAutoConfiguration.class)
@ConditionalOnProperty(prefix = CommonConstant.PROPERTY_PREFIX + ".doc.global-security-scheme", name = "enabled",
		havingValue = "true")
public class GlobalSecuritySchemeConfig {

	@Bean
	public SecuritySchemePostProcessor securitySchemePostProcessor() {
		return new SecuritySchemePostProcessor();
	}

}
