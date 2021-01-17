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

package com.power4j.ji.common.core.context;

import com.power4j.ji.common.core.constant.CommonConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/14
 * @since 1.0
 */
@Getter
@Setter
@ConfigurationProperties(FlygonContextProperties.PREFIX)
public class FlygonContextProperties {

	public final static String PREFIX = CommonConstant.PROPERTY_PREFIX + ".context";

	private HeaderMapping headerMapping = new HeaderMapping();

	private List<String> custom = new ArrayList<>();

	@Getter
	@Setter
	public static class HeaderMapping {

		private String requestId = "x-request-id";

		private String accountId = "x-account-id";

	}

	public List<String> getHeaders() {
		List<String> headers = Arrays.asList(headerMapping.getAccountId(), headerMapping.getRequestId());
		headers.addAll(custom);
		return headers;
	}

}
