package com.power4j.flygon.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/10
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@TableName("t_sys_resource_grantee")
public class SysResourceGrantee implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 资源ID
	 */
	private Long resourceId;

}
