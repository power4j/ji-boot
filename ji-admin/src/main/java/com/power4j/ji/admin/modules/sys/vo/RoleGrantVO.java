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

package com.power4j.ji.admin.modules.sys.vo;

import com.power4j.ji.common.core.validate.Groups;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/18
 * @since 1.0
 */
@Data
@Schema(title = "角色授权表单")
public class RoleGrantVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 授权类型 0 普通 1 可二次授权
	 */
	@Schema(description = "授权类型 0 普通 1 可二次授权", example = "0")
	@NotNull(groups = { Groups.Default.class })
	@Pattern(regexp = "0|1", message = "状态只能是 0 或者 1", groups = { Groups.Default.class })
	private String grantType;

	/**
	 * 角色ID
	 */
	@Schema(description = "角色ID")
	@NotNull(groups = { Groups.Default.class })
	private Long roleId;

}
