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

package com.power4j.ji.admin.modules.sys.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.power4j.ji.admin.modules.sys.component.SysResourcePathBuilder;
import com.power4j.ji.admin.modules.sys.constant.CacheConstant;
import com.power4j.ji.admin.modules.sys.constant.ResourceTypeEnum;
import com.power4j.ji.admin.modules.sys.constant.SysConstant;
import com.power4j.ji.admin.modules.sys.dao.SysResourceMapper;
import com.power4j.ji.admin.modules.sys.dto.SysResourceDTO;
import com.power4j.ji.admin.modules.sys.entity.SysResource;
import com.power4j.ji.admin.modules.sys.entity.SysResourceNode;
import com.power4j.ji.admin.modules.sys.service.SysResourceService;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.exception.BizException;
import com.power4j.ji.common.core.util.TreeUtil;
import com.power4j.ji.common.data.crud.entity.BaseEntity;
import com.power4j.ji.common.data.crud.service.impl.AbstractTreeNodeCrudService;
import com.power4j.ji.common.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/30
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SysResourceServiceImpl extends
		AbstractTreeNodeCrudService<SysResourceMapper, SysResourceDTO, SysResource, SysResourceNode, SysResourcePathBuilder>
		implements SysResourceService {

	private final SysResourcePathBuilder pathBuilder;

	@Caching(evict = { @CacheEvict(cacheNames = { CacheConstant.Name.RESOURCE_TREE }, allEntries = true),
			@CacheEvict(cacheNames = { CacheConstant.Name.ROLE_CODES_TO_RESOURCES }, allEntries = true),
			@CacheEvict(cacheNames = { CacheConstant.Name.ROLE_CODES_TO_RESOURCE_TREE }, allEntries = true,
					condition = "#result != null") })
	@Transactional(rollbackFor = Exception.class)
	@Override
	public SysResourceDTO post(SysResourceDTO dto) {
		if (CharSequenceUtil.isEmpty(dto.getPath()) && !ResourceTypeEnum.BUTTON.getValue().equals(dto.getType())) {
			throw new BizException(SysErrorCodes.E_BAD_REQUEST, "菜单和路由需要指定path");
		}
		return super.post(dto);
	}

	@Caching(evict = {
			// @formatter:off
			@CacheEvict(cacheNames = { CacheConstant.Name.RESOURCE_TREE }, allEntries = true),
			@CacheEvict(cacheNames = { CacheConstant.Name.ROLE_CODES_TO_RESOURCES }, allEntries = true),
			@CacheEvict(cacheNames = { CacheConstant.Name.ROLE_CODES_TO_RESOURCE_TREE }, allEntries = true,condition = "#result != null")
			// @formatter:on
	})
	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysResourceDTO put(SysResourceDTO dto) {
		if (CharSequenceUtil.isEmpty(dto.getPath()) && !ResourceTypeEnum.BUTTON.getValue().equals(dto.getType())) {
			throw new BizException(SysErrorCodes.E_BAD_REQUEST, "菜单和路由需要指定path");
		}
		return super.put(dto);
	}

	@Caching(evict = {
			// @formatter:off
			@CacheEvict(cacheNames = { CacheConstant.Name.RESOURCE_TREE }, allEntries = true,condition = "#result != null"),
			@CacheEvict(cacheNames = { CacheConstant.Name.ROLE_CODES_TO_RESOURCE_TREE }, allEntries = true,condition = "#result != null"),
			@CacheEvict(cacheNames = { CacheConstant.Name.ROLE_CODES_TO_RESOURCES }, allEntries = true,condition = "#result != null")
			// @formatter:on
	})
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Optional<SysResourceDTO> delete(Serializable id) {
		return super.delete(id);
	}

	@Override
	protected SysResourcePathBuilder getPathBuilder() {
		return pathBuilder;
	}

	@Transactional(rollbackFor = Exception.class)
	@Cacheable(cacheNames = CacheConstant.Name.RESOURCE_TREE, key = "'node:'+#rootId")
	@Override
	public List<SysResourceDTO> getTreeNodes(Long rootId) {
		return super.getTreeNodes(rootId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Cacheable(cacheNames = CacheConstant.Name.RESOURCE_TREE, key = "'tree:'+#rootId")
	@Override
	public SysResourceDTO getTree(Long rootId) {
		return super.getTree(rootId);
	}

	@Cacheable(cacheNames = CacheConstant.Name.ROLE_CODES_TO_RESOURCES, key = "@keyMaker.makeKeyStr(#roleCodes)")
	@Override
	public List<SysResource> listForRoles(Collection<String> roleCodes) {
		if (roleCodes == null || roleCodes.isEmpty()) {
			return Collections.emptyList();
		}
		return getBaseMapper().selectByRoles(roleCodes);
	}

	@Override
	public List<SysResource> listAll() {
		return list();
	}

	@Cacheable(cacheNames = CacheConstant.Name.ROLE_CODES_TO_RESOURCE_TREE, key = "@keyMaker.makeKeyStr(#roleCodes)")
	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<SysResourceDTO> getTreeForRoles(Collection<String> roleCodes) {
		if (roleCodes.isEmpty()) {
			return Collections.emptyList();
		}
		Set<Long> granted = listForRoles(SecurityUtil.getLoginUserRoles()).stream().map(BaseEntity::getId)
				.collect(Collectors.toSet());
		List<SysResourceDTO> full = getTreeNodes(SysConstant.ROOT_RESOURCE_ID);
		return TreeUtil.filterNode(full, node -> granted.contains(node.getNodeId()));
	}

	@Override
	public int countResourceName(String name, Long ignoreId) {
		return countByColumn("name", name, ignoreId);
	}

	@Override
	public int countResourcePath(String path, @Nullable Long ignoreId) {
		return countByColumn("path", path, ignoreId);
	}

	@Override
	protected List<SysResourceDTO> fetchChildren(Long rootId, List<SysResourceDTO> defVal) {
		return super.fetchChildren(rootId, defVal).stream().sorted(Comparator.comparingInt(SysResourceDTO::getSort))
				.collect(Collectors.toList());
	}

}
