/*
 * Copyright 2020 ChenJun (power4j@outlook.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.power4j.ji.admin.modules.sys.service;

import com.power4j.ji.admin.modules.sys.dto.SysResourceDTO;
import com.power4j.ji.admin.modules.sys.entity.SysResource;
import com.power4j.ji.common.data.crud.service.CrudService;
import org.springframework.lang.Nullable;

import java.util.Collection;
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
	List<SysResourceDTO> getTreeNodes(Long rootId);

	/**
	 * 返回树
	 * @param rootId
	 * @return
	 */
	SysResourceDTO getTree(Long rootId);

	/**
	 * 查询列表
	 * @param roleCodes
	 * @return
	 */
	List<SysResource> listForRoles(@Nullable Collection<String> roleCodes);

	/**
	 * 全部数据
	 * @return
	 */
	List<SysResource> listAll();

	/**
	 * 查询角色的资源,并构建为树
	 * @param roleCodes
	 * @return
	 */
	List<SysResourceDTO> getTreeForRoles(@Nullable Collection<String> roleCodes);

	/**
	 * 统计使用次数
	 * @param name
	 * @param ignoreId 排除的ID
	 * @return
	 */
	long countResourceName(String name, @Nullable Long ignoreId);

	/**
	 * 统计使用次数
	 * @param path
	 * @param ignoreId 排除的ID
	 * @return
	 */
	long countResourcePath(String path, @Nullable Long ignoreId);

}
