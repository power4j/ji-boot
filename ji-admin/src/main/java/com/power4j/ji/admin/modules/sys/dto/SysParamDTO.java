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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/9
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysParamDTO extends BaseDTO implements Serializable {

	/**
	 * 参数键,唯一
	 */
	@Schema(description = "参数名,唯一", example = "project-home")
	@NotBlank(groups = { Groups.Default.class })
	@Size(min = 1, max = 255, groups = { Groups.Default.class })
	private String paramKey;

	/**
	 * 参数值
	 */
	@Schema(description = "值", example = "power4j.com")
	@NotBlank(groups = { Groups.Default.class })
	@Size(min = 1, max = 2000, groups = { Groups.Default.class })
	private String paramValue;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	@Size(min = 1, max = 40, groups = { Groups.Default.class })
	private String remarks;

	/**
	 * 状态 0 有效 1 停用
	 */
	@Schema(description = "状态 0 有效 1 停用", example = "0")
	@NotNull(groups = { Groups.Default.class })
	@Pattern(regexp = "0|1", message = "状态只能是 0 或者 1", groups = { Groups.Default.class })
	private String status;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String createBy;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String updateBy;

}
