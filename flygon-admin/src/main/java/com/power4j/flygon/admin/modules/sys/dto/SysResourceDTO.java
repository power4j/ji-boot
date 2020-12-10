package com.power4j.flygon.admin.modules.sys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.power4j.flygon.common.core.model.Node;
import com.power4j.flygon.common.core.validate.Groups;
import com.power4j.flygon.common.data.crud.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
	 * 资源代码
	 */
	@Schema(description = "资源名称", example = "home-page")
	@NotNull(groups = { Groups.Default.class })
	@Size(min = 1, max = 20, groups = { Groups.Default.class })
	private String name;

	/**
	 * 显示名称
	 */
	@Schema(description = "显示名称", example = "home-page")
	@NotNull(groups = { Groups.Default.class })
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
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Boolean hasChildren;

	/**
	 * 子节点
	 */
	@Schema(description = "子节点", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
