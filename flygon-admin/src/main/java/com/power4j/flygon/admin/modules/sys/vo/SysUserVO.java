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

package com.power4j.flygon.admin.modules.sys.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.power4j.flygon.common.core.constant.RegxPattern;
import com.power4j.flygon.common.core.validate.Groups;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-19
 * @since 1.0
 */
@Data
public class SysUserVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主健
	 */
	@Schema(description = "主健", accessMode = Schema.AccessMode.READ_ONLY)
	@NotNull(groups = { Groups.Update.class })
	@Null(groups = { Groups.Create.class })
	private Long id;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", accessMode = Schema.AccessMode.READ_ONLY)
	@Null
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String createBy;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
	@Null
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime createAt;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", accessMode = Schema.AccessMode.READ_ONLY)
	@Null
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String updateBy;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
	@Null
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime updateAt;

	/**
	 * 登录用户名
	 */
	@Schema(description = "用户名")
	@Size(min = 6, max = 20, groups = { Groups.Default.class })
	private String username;

	/**
	 * 密码
	 */
	@Schema(description = "密码")
	@Size(min = 6, max = 20, groups = { Groups.Default.class })
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	/**
	 * 姓名
	 */
	@Schema(description = "姓名")
	@Size(min = 2, max = 20, groups = { Groups.Default.class })
	private String name;

	/**
	 * 邮箱
	 */
	@Schema(description = "邮箱")
	@Size(min = 6, max = 20, groups = { Groups.Default.class })
	@Email(groups = { Groups.Default.class })
	private String mail;

	/**
	 * 手机号码
	 */
	@Schema(description = "手机号码")
	@Pattern(regexp = RegxPattern.MOBILE_PHONE_NUMBER, groups = { Groups.Default.class }, message = "不是一个合法的手机号码")
	private String mobilePhone;

	/**
	 * 管理员备注
	 */
	@Schema(description = "管理员备注")
	@Size(max = 20, groups = { Groups.Default.class })
	private String remarks;

	/**
	 * 状态 0 有效 1 停用
	 */
	@Schema(description = "状态 0 有效 1 停用", example = "0")
	@NotNull(groups = { Groups.Default.class, Groups.Update.class })
	@Pattern(regexp = "0|1", message = "状态只能是 0 或者 1")
	private Integer status;

}
