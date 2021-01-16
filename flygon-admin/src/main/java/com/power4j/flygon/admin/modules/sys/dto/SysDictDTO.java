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

package com.power4j.flygon.admin.modules.sys.dto;

import com.power4j.flygon.common.core.validate.Groups;
import com.power4j.flygon.common.data.crud.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 代码，唯一
	 */
	@Schema(description = "代码，唯一", example = "web-color")
	@NotBlank(groups = { Groups.Default.class })
	@Size(min = 2, max = 40, groups = { Groups.Default.class })
	private String code;

	/**
	 * 名称
	 */
	@Schema(description = "名称", example = "color")
	@NotBlank(groups = { Groups.Default.class })
	@Size(max = 40, groups = { Groups.Default.class })
	private String name;

	/**
	 * 备注
	 */
	@Schema(description = "备注", example = "colors")
	@Size(max = 20, groups = { Groups.Default.class })
	private String remarks;

}
