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

package com.power4j.ji.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.power4j.ji.common.data.crud.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-19
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_user")
public class SysUser extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 登录用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 加密盐
	 */
	private String salt;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 邮箱
	 */
	private String mail;

	/**
	 * 手机号码
	 */
	private String mobilePhone;

	/**
	 * 管理员备注
	 */
	private String remarks;

	/**
	 * 状态 0 有效 1 停用
	 */
	private String status;

	/**
	 * 创建人
	 */
	private String createBy;

	/**
	 * 更新人
	 */
	private String updateBy;

}
