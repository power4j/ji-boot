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

package com.power4j.ji.admin.modules.ureport.dto;

import com.power4j.ji.common.core.validate.Groups;
import com.power4j.ji.common.data.crud.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/2/12
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UrFileDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "名称", example = "test")
	@NotBlank(groups = { Groups.Default.class })
	@Size(min = 1, max = 255, groups = { Groups.Default.class })
	private String file;

}
