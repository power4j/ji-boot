package com.power4j.flygon.admin.modules.sys.service;

import com.power4j.flygon.admin.modules.sys.dto.SysRoleGrantDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysRoleGrant;
import com.power4j.flygon.common.data.crud.service.CrudService;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/14
 * @since 1.0
 */
public interface SysRoleGrantService extends CrudService<SysRoleGrantDTO, SysRoleGrant> {
	/**
	 * 清除授权
	 * @param userId
	 * @return
	 */
	int removeByUser(Long userId);

	/**
	 * 添加授权
	 * @param userId
	 * @param roles
	 * @return
	 */
	List<SysRoleGrant> add(Long userId, List<SysRoleGrantDTO> roles);

	/**
	 * 重置授权
	 * @param userId
	 * @param roles
	 * @return
	 */
	List<SysRoleGrant> setRoles(Long userId, @Nullable List<SysRoleGrantDTO> roles);

	/**
	 * 列表查询
	 * @param userId
	 * @param grantType
	 * @return
	 */
	List<SysRoleGrant> getByUser(Long userId, @Nullable String grantType);
}
