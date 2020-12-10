package com.power4j.flygon.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.power4j.flygon.common.data.crud.entity.BaseEntity;
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
