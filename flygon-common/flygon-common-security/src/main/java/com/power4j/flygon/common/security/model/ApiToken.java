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

package com.power4j.flygon.common.security.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/24
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class ApiToken implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "访问令牌")
	private String token;

	@Schema(description = "用户UID")
	private Long uuid;

	@Schema(description = "登录名")
	private String username;

	@Schema(description = "姓名")
	private String name;

	@Schema(description = "过期时间(yyyy-MM-dd'T'HH:mm:ss'Z')", example = "2020-01-01T20:05:01Z")
	private LocalDateTime expireIn;

	@Schema(description = "发布者")
	private String issuedBy;

	@Schema(description = "其他信息")
	private Map<String, Object> additionalInformation = Collections.emptyMap();

}
