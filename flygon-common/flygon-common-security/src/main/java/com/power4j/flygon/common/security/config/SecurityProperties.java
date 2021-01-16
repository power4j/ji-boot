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

	private String loginUrl = "/login";

	private String logoutUrl = "/logout";

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
