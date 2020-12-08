package com.power4j.flygon.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.power4j.flygon.common.data.crud.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/9
 * @since 1.0
 */
@Data
@TableName("t_sys_param")
@EqualsAndHashCode(callSuper = false)
public class SysParam extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 参数键,唯一
	 */
	private String paramKey;

	/**
	 * 参数值
	 */
	private String paramValue;

	/**
	 * 备注
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
