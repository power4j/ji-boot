package com.power4j.flygon.common.security.config;

import com.power4j.flygon.common.core.constant.CommonConstant;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	/**
	 * 对外暴露的 URL
	 */
	private List<MvcMatcher> expose = new ArrayList<>();

	@Data
	public static class MvcMatcher {

		/**
		 * Ant风格URL表达式,如{@code  /api/** }
		 */
		String pattern;

		/**
		 * HTTP 方法(可以为空,表示匹配所有方法):
		 * <ul>
		 * <li>GET</li>
		 * <li>HEAD</li>
		 * <li>POST</li>
		 * <li>PUT</li>
		 * <li>PATCH</li>
		 * <li>DELETE</li>
		 * <li>OPTIONS</li>
		 * <li>TRACE</li>
		 * </ul>
		 */
		Set<String> methods = new HashSet<>();

	}

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
