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

import com.power4j.ji.common.core.validate.Groups;
import com.power4j.ji.common.data.crud.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictItemDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典ID
	 */
	@Schema(description = "字典ID")
	@NotNull(groups = { Groups.Default.class })
	private Long dictId;

	/**
	 * 值
	 */
	@Schema(description = "值", example = "1")
	@NotBlank(groups = { Groups.Default.class })
	@Size(max = 255, groups = { Groups.Default.class })
	private String value;

	/**
	 * 标签
	 */
	@Schema(description = "标签", example = "red color")
	@NotBlank(groups = { Groups.Default.class })
	@Size(max = 250, groups = { Groups.Default.class })
	private String label;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	@Size(max = 40, groups = { Groups.Default.class })
	private String remarks;

}
