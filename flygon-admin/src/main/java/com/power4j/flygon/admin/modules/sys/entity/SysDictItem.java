package com.power4j.flygon.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.power4j.flygon.common.data.crud.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_dict_item")
public class SysDictItem extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 字典ID
	 */
	private Long dictId;

	/**
	 * 值
	 */
	private String value;

	/**
	 * 标签
	 */
	private String label;

	/**
	 * 备注
	 */
	private String remarks;
}
