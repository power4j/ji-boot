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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.power4j.flygon.common.core.constant.RegxPattern;
import com.power4j.flygon.common.core.validate.Groups;
import com.power4j.flygon.common.data.crud.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-19
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SysUserDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 登录用户名
	 */
	@Schema(description = "用户名", example = "power4j")
	@Size(min = 3, max = 20, groups = { Groups.Default.class })
	private String username;

	/**
	 * 密码
	 */
	@Schema(description = "密码", example = "a123Z456", accessMode = Schema.AccessMode.WRITE_ONLY)
	@NotBlank(groups = { Groups.Create.class })
	@Size(min = 6, max = 20, groups = { Groups.Default.class })
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	/**
	 * 姓名
	 */
	@Schema(description = "姓名", example = "momo")
	@NotBlank(groups = { Groups.Default.class })
	@Size(max = 20, groups = { Groups.Default.class })
	private String name;

	/**
	 * 邮箱
	 */
	@Schema(description = "邮箱", example = "cj@power4j.com")
	@Size(max = 40, groups = { Groups.Default.class })
	@Email(groups = { Groups.Default.class })
	private String mail;

	/**
	 * 手机号码
	 */
	@Schema(description = "手机号码", example = "+8618081020301")
	@Pattern(regexp = RegxPattern.MOBILE_PHONE_NUMBER, groups = { Groups.Default.class }, message = "不是一个合法的手机号码")
	private String mobilePhone;

	/**
	 * 管理员备注
	 */
	@Schema(description = "管理员备注", example = "test user")
	@Size(max = 20, groups = { Groups.Default.class })
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
