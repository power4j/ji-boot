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
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/17
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysOrgNodeDTO extends BaseDTO implements Node<SysOrgNodeDTO>, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@Schema(description = "名称", example = "成都分公司")
	@NotBlank(groups = { Groups.Default.class })
	@Size(min = 1, max = 40, groups = { Groups.Default.class })
	private String name;

	/**
	 * 组织代码
	 */
	@Schema(description = "组织代码", example = "028-001")
	@NotBlank(groups = { Groups.Default.class })
	@Size(min = 1, max = 20, groups = { Groups.Default.class })
	private String code;
	/**
	 * 排序
	 */
	@Schema(description = "排序", example = "0")
	private Integer sort;

	/**
	 * 自定义标签
	 */
	@Schema(description = "自定义标签,取自业务字典 sys_org_tags", example = "0")
	@Size(max = 8, groups = { Groups.Default.class })
	private String tag;

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
	private List<SysOrgNodeDTO> children;

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
	public List<SysOrgNodeDTO> getNextNodes() {
		return getChildren();
	}

	@Override
	public void setNextNodes(List<SysOrgNodeDTO> children) {
		setChildren(children);
	}

	@Override
	public void setHasMoreNodes(boolean val) {
		hasChildren = val;
	}
}
