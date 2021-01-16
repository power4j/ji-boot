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

package com.power4j.flygon.common.security.config;

import com.power4j.flygon.common.core.constant.CommonConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/26
 * @since 1.0
 */
@Getter
@Setter
@ConfigurationProperties(SecureAccessProperties.PREFIX)
public class SecureAccessProperties {

	public static final String PREFIX = CommonConstant.PROPERTY_PREFIX + ".security.access";

	/**
	 * 开启此功能
	 */
	private boolean enabled = true;

	/**
	 * 忽略的web资源,一般用于放行静态资源
	 */
	private PathMatcher ignore = new PathMatcher();

	/**
	 * 自定义配置
	 */
	public List<HttpAccess> filters = new ArrayList<>();

	@Getter
	@Setter
	public static class PathMatcher {

		/**
		 * Ant风格URL表达式,如{@code  /api/** }
		 */
		List<String> patterns = new ArrayList<>();

	}

	@Getter
	@Setter
	public static class MvcMatcher extends PathMatcher {

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
		private Set<String> methods = new HashSet<>();

	}

	@Getter
	@Setter
	public static class HttpAccess extends MvcMatcher {

		/**
		 * 访问控制
		 * <ul>
		 * <li>permitAll</li>
		 * <li>denyAll</li>
		 * <li>anonymous</li>
		 * <li>authenticated</li>
		 * <li>fullyAuthenticated</li<li>rememberMe</li>
		 * <li>hasAnyRole("xxx")</li>
		 * <li>hasRole("xxx")</li>
		 * <li>hasAnyAuthority("xxx")</li>
		 * <li>hasAuthority("xxx")</li>
		 * <li>hasIpAddress("xxx")</li>
		 * </ul>
		 */
		private String access = "authenticated";

	}

}
