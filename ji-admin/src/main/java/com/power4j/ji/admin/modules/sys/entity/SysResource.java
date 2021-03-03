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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.power4j.ji.common.data.crud.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/26
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_resource")
public class SysResource extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单类型
	 */
	private String type;

	/**
	 * 菜单名称
	 */
	private String name;

	/**
	 * 显示名称
	 */
	private String title;

	/**
	 * 权限代码
	 */
	private String permission;

	/**
	 * 前端路径
	 */
	private String path;

	/**
	 * 前端组件路径
	 */
	private String component;

	/**
	 * 路由缓冲
	 */
	private Boolean cache;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 父节点
	 */
	@TableField(exist = false)
	private Long parentId;

}
