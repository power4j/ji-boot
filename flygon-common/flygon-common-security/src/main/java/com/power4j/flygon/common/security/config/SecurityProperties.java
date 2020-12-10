package com.power4j.flygon.common.security.config;

import com.power4j.flygon.common.core.constant.CommonConstant;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/25
 * @since 1.0
 */
@Getter
@Setter
@ConfigurationProperties(SecurityProperties.PREFIX)
public class SecurityProperties {

	public static final String PREFIX = CommonConstant.PROPERTY_PREFIX + ".security";

	private ApiTokenProperties apiToken = new ApiTokenProperties();

	@Data
	public static class ApiTokenProperties {

		/**
		 * 过期时间,秒
		 */
		private Long expireSec = 3600 * 24L;

		/**
		 * 发布者
		 */
		private String issueBy = "power4j.com";

	}

}
