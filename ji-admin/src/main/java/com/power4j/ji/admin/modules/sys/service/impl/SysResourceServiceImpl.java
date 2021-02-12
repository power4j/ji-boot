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

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
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
import com.power4j.ji.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.ji.common.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
public class SysResourceServiceImpl extends AbstractCrudService<SysResourceMapper, SysResourceDTO, SysResource>
		implements SysResourceService {

	private final SysResourcePathBuilder pathBuilder;

	@Caching(evict = { @CacheEvict(cacheNames = { CacheConstant.Name.RESOURCE_TREE }, allEntries = true),
			@CacheEvict(cacheNames = { CacheConstant.Name.ROLE_CODES_TO_RESOURCES }, allEntries = true),
			@CacheEvict(cacheNames = { CacheConstant.Name.ROLE_CODES_TO_RESOURCE_TREE }, allEntries = true,
					condition = "#result != null") })
	@Transactional(rollbackFor = Exception.class)
	@Override
	public SysResourceDTO post(SysResourceDTO dto) {
		if (StrUtil.isEmpty(dto.getPath()) && !ResourceTypeEnum.BUTTON.getValue().equals(dto.getType())) {
			throw new BizException(SysErrorCodes.E_BAD_REQUEST, "菜单和路由需要指定path");
		}
		dto = super.post(dto);
		pathBuilder.insertNode(dto.getParentId(), dto.getId());
		return dto;
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
		if (StrUtil.isEmpty(dto.getPath()) && !ResourceTypeEnum.BUTTON.getValue().equals(dto.getType())) {
			throw new BizException(SysErrorCodes.E_BAD_REQUEST, "菜单和路由需要指定path");
		}
		List<SysResourceNode> nodes = pathBuilder.loadAncestors(dto.getNodeId(), 1, 1);
		if (nodes.isEmpty()) {
			throw new BizException(SysErrorCodes.E_BAD_REQUEST, "无效数据");
		}
		Assert.isTrue(nodes.size() == 1);
		if (!nodes.get(0).getAncestor().equals(dto.getParentId())) {
			pathBuilder.removeNode(dto.getId());
			pathBuilder.insertNode(dto.getParentId(), dto.getId());
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
		pathBuilder.removeNode(id);
		return super.delete(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<SysResourceDTO> getChildren(Long rootId) {
		List<SysResourceDTO> children = fetchChildren(rootId, Collections.emptyList());
		if (!children.isEmpty()) {
			Map<Long, Long> layer = pathBuilder
					.loadDescendants(children.stream().map(SysResourceDTO::getNodeId).collect(Collectors.toList()), 1,
							1)
					.stream().collect(Collectors.toMap(SysResourceNode::getAncestor, SysResourceNode::getDescendant,
							(v1, v2) -> v2));
			children.forEach(o -> o.setHasChildren(layer.containsKey(o.getNodeId())));
		}

		return children;
	}

	@Transactional(rollbackFor = Exception.class)
	@Cacheable(cacheNames = CacheConstant.Name.RESOURCE_TREE, key = "'node:'+#rootId")
	@Override
	public List<SysResourceDTO> getTreeNodes(Long rootId) {
		List<SysResourceDTO> nodes = getChildren(rootId);
		nodes.forEach(node -> buildTree(node));
		return nodes;
	}

	@Transactional(rollbackFor = Exception.class)
	@Cacheable(cacheNames = CacheConstant.Name.RESOURCE_TREE, key = "'tree:'+#rootId")
	@Override
	public SysResourceDTO getTree(Long rootId) {
		SysResourceDTO root = read(rootId).orElse(null);
		if (root != null) {
			root.setChildren(getTreeNodes(rootId));
		}
		return root;
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
		return list(null);
	}

	@Cacheable(cacheNames = CacheConstant.Name.ROLE_CODES_TO_RESOURCE_TREE, key = "@keyMaker.makeKeyStr(#roleCodes)")
	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<SysResourceDTO> getTreeForRoles(Collection<String> roleCodes) {
		if (roleCodes.isEmpty()) {
			return Collections.emptyList();
		}
		Set<Long> granted = listForRoles(SecurityUtil.getLoginUserRoles()).stream().map(o -> o.getId())
				.collect(Collectors.toSet());
		List<SysResourceDTO> full = getTreeNodes(SysConstant.ROOT_RESOURCE_ID);
		return TreeUtil.filterNode(full, node -> granted.contains(node.getNodeId()));
	}

	@Override
	public int countResourceName(String name, Long ignoreId) {
		return countByColumn("name", name, ignoreId);
	}

	protected void buildTree(SysResourceDTO node) {
		node.setNextNodes(getChildren(node.getNodeId()));
		if (node.getNextNodes() != null) {
			node.getNextNodes().forEach(o -> buildTree(o));
		}
	}

	protected List<SysResourceDTO> fetchChildren(Long rootId, List<SysResourceDTO> defVal) {
		List<Long> ids = pathBuilder.loadDescendants(rootId, 1, 1).stream().map(SysResourceNode::getDescendant)
				.collect(Collectors.toList());
		if (ids.isEmpty()) {
			return defVal;
		}
		List<SysResource> children = getBaseMapper().selectBatchIds(ids);
		children.forEach(o -> o.setParentId(rootId));
		return toDtoList(children).stream().sorted(Comparator.comparingInt(SysResourceDTO::getSort))
				.collect(Collectors.toList());
	}

}
