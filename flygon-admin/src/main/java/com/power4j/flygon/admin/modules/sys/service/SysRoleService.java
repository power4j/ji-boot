package com.power4j.flygon.admin.modules.sys.service;

import com.power4j.flygon.admin.modules.sys.dto.SysRoleDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysRole;
import com.power4j.flygon.common.data.crud.service.CrudService;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/10
 * @since 1.0
 */
public interface SysRoleService extends CrudService<SysRoleDTO, SysRole> {

	/**
	 * 统计编码使用次数
	 * @param code
	 * @param ignoreId 排除的ID
	 * @return
	 */
	int countRoleCode(String code, @Nullable Long ignoreId);

	/**
	 * 角色列表
	 * @param username
	 * @param grantType
	 * @return
	 */
	List<SysRole> listForUser(String username,@Nullable String grantType);
}
