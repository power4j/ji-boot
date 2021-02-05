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

import com.power4j.ji.common.core.model.Node;
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
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysResourceDTO extends BaseDTO implements Node<SysResourceDTO>, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单类型
	 */
	@Schema(description = "菜单类型", example = "1")
	@NotNull(groups = { Groups.Default.class })
	@Pattern(regexp = "1|2|3", message = "菜单类型只能是 1,2,3", groups = { Groups.Default.class })
	private String type;

	/**
	 * 路由名称
	 */
	@Schema(description = "路由名称", example = "home-page")
	@NotBlank(groups = { Groups.Default.class })
	@Size(min = 1, max = 240, groups = { Groups.Default.class })
	private String name;

	/**
	 * 显示名称
	 */
	@Schema(description = "显示名称", example = "home-page")
	@NotBlank(groups = { Groups.Default.class })
	@Size(min = 1, max = 20, groups = { Groups.Default.class })
	private String title;

	/**
	 * 权限代码
	 */
	@Schema(description = "权限代码")
	@Size(max = 240, groups = { Groups.Default.class })
	private String permission;

	/**
	 * 路由地址
	 */
	@Schema(description = "路由地址", example = "/home")
	@Size(max = 240, groups = { Groups.Default.class })
	private String path;

	/**
	 * 前端组件路径
	 */
	@Schema(description = "路由组件", example = "layoutHeaderAside")
	@Size(max = 240, groups = { Groups.Default.class })
	private String component;

	/**
	 * 图标
	 */
	@Schema(description = "图标")
	@Size(max = 40, groups = { Groups.Default.class })
	private String icon;

	/**
	 * 排序
	 */
	@Schema(description = "排序", example = "0")
	private Integer sort;

	/**
	 * 父节点
	 */
	@Schema(description = "父节点", example = "0")
	@NotNull(groups = { Groups.Default.class })
	private Long parentId;

	/**
	 * 是否包含子节点
	 */
	@Schema(description = "是否包含子节点", accessMode = Schema.AccessMode.READ_ONLY)
	private Boolean hasChildren;

	/**
	 * 子节点
	 */
	@Schema(description = "子节点", accessMode = Schema.AccessMode.READ_ONLY)
	private List<SysResourceDTO> children;

	@Override
	public Long getNodeId() {
		return getId();
	}

	@Override
	public void setNodeId(Long id) {
		setId(id);
	}

	@Override
	public Long getNodePid() {
		return getParentId();
	}

	@Override
	public void setNodePid(Long pid) {
		setParentId(pid);
	}

	@Override
	public List<SysResourceDTO> getNextNodes() {
		return getChildren();
	}

	@Override
	public void setNextNodes(List<SysResourceDTO> children) {
		setChildren(children);
	}

}
