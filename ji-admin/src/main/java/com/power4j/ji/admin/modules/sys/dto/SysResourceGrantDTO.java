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

package com.power4j.ji.admin.modules.sys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/14
 * @since 1.0
 */
@Data
@Schema(title = "资源授权")
public class SysResourceGrantDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@Schema(description = "角色ID", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long roleId;

	/**
	 * 资源ID
	 */
	@Schema(description = "资源ID")
	private Long resourceId;

}
