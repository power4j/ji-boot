package com.power4j.flygon.admin.modules.sys.service;

import com.power4j.flygon.admin.modules.sys.dto.SysResourceDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysResource;
import com.power4j.flygon.common.data.crud.service.CrudService;

import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/30
 * @since 1.0
 */
public interface SysResourceService extends CrudService<SysResourceDTO, SysResource> {

	/**
	 * 取下级
	 * @param rootId
	 * @return 如果没有下级返回空列表
	 */
	List<SysResourceDTO> getChildren(Long rootId);

	/**
	 * 查询下级节点,并构建为树
	 * @param rootId
	 * @return
	 */
	List<SysResourceDTO> getNodes(Long rootId);

	/**
	 * 返回树
	 * @param rootId
	 * @return
	 */
	SysResourceDTO getTree(Long rootId);
}
