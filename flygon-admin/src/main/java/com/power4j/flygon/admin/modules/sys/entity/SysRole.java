package com.power4j.flygon.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.power4j.flygon.common.data.crud.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/10
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_role")
public class SysRole extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 状态 0 正常 1 禁用
	 */
	private String status;

	/**
	 * 拥有者
	 */
	private Long owner;

}
