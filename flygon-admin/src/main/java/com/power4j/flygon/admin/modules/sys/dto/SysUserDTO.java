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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-19
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SysUserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主健
	 */
	@Schema(description = "主健")
	@NotNull(groups = { Groups.Update.class })
	private Long id;

	/**
	 * 登录用户名
	 */
	@Schema(description = "登录用户名")
	@NotNull(groups = { Groups.Create.class, Groups.Update.class })
	@Size(min = 4, max = 20)
	private String username;

	/**
	 * 密码
	 */
	@Schema(description = "密码")
	@NotNull(groups = { Groups.Create.class })
	@Size(min = 8, max = 20)
	private String password;

	/**
	 * 姓名
	 */
	@Schema(description = "姓名")
	@NotNull(groups = { Groups.Create.class, Groups.Update.class })
	@Size(min = 2, max = 20)
	private String name;

	/**
	 * 邮箱
	 */
	@Schema(description = "邮箱")
	@Email
	private String mail;

	/**
	 * 手机号码
	 */
	@Schema(description = "手机号码")
	private String mobilePhone;

	/**
	 * 管理员备注
	 */
	@Schema(description = "管理员备注")
	private String remarks;

	/**
	 * 状态 0 有效 1 停用
	 */
	@Schema(description = "状态 0 有效 1 停用")
	private Integer status;

}
