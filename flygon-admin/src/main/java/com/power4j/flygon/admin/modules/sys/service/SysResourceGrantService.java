package com.power4j.flygon.admin.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.power4j.flygon.admin.modules.sys.entity.SysResourceGrantee;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/14
 * @since 1.0
 */
public interface SysResourceGrantService extends IService<SysResourceGrantee> {

	/**
	 * 清除授权
	 * @param roleId
	 * @return
	 */
	int removeByRole(Long roleId);

	/**
	 * 添加授权
	 * @param roleId
	 * @param resourceIds
	 * @return
	 */
	List<SysResourceGrantee> add(Long roleId, Collection<Long> resourceIds);

	/**
	 * 重置授权
	 * @param roleId
	 * @param resourceIds
	 * @return
	 */
	List<SysResourceGrantee> setResources(Long roleId, @Nullable Collection<Long> resourceIds);

	/**
	 * 列表查询
	 * @param roleId
	 * @return
	 */
	List<SysResourceGrantee> getByRole(Long roleId);

}
