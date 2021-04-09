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
import com.power4j.ji.common.core.validate.Groups;
import com.power4j.ji.common.data.crud.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/4/9
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SysPositionDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编码
	 */
	@Schema(description = "编码", example = "IT.Admin")
	@Size(min = 3, max = 20, groups = { Groups.Default.class })
	private String code;

	/**
	 * 名称
	 */
	@Schema(description = "名称", example = "IT admin")
	@Size(min = 6, max = 20, groups = { Groups.Default.class })
	private String name;

	/**
	 * 状态 0 正常 1 禁用
	 */
	@Schema(description = "状态 0 有效 1 停用", example = "0")
	@NotNull(groups = { Groups.Default.class })
	@Pattern(regexp = "[01]", message = "状态只能是 0 或者 1", groups = { Groups.Default.class })
	private String status;

	/**
	 * 拥有者
	 */
	@Schema(description = "拥有者", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long owner;

	/**
	 * 备注
	 */
	@Schema(description = "备注", example = "test")
	@Size(max = 20, groups = { Groups.Default.class })
	private String remarks;

}
