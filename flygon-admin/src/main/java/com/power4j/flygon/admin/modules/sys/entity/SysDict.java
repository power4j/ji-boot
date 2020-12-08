package com.power4j.flygon.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.power4j.flygon.common.data.crud.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 字典
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_dict")
public class SysDict extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 代码，唯一
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 备注
	 */
	private String remarks;
}
