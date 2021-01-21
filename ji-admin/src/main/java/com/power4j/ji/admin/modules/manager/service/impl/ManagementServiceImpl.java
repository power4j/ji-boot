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

package com.power4j.ji.admin.modules.manager.service.impl;

import com.power4j.ji.admin.modules.manager.service.ManagementService;
import com.power4j.ji.admin.modules.sys.constant.CacheConstant;
import com.power4j.ji.admin.modules.sys.dto.SysResourceDTO;
import com.power4j.ji.admin.modules.sys.dto.SysRoleDTO;
import com.power4j.ji.admin.modules.sys.entity.SysResource;
import com.power4j.ji.admin.modules.sys.entity.SysRole;
import com.power4j.ji.admin.modules.sys.service.SysResourceGrantService;
import com.power4j.ji.admin.modules.sys.service.SysResourceService;
import com.power4j.ji.admin.modules.sys.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/20
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ManagementServiceImpl implements ManagementService {

	private final SysResourceService sysResourceService;

	private final SysResourceGrantService sysResourceGrantService;

	@Caching(evict = { @CacheEvict(cacheNames = { CacheConstant.Name.ROLE_CODES_TO_RESOURCE_TREE }, allEntries = true),
			@CacheEvict(cacheNames = { CacheConstant.Name.ROLE_CODES_TO_RESOURCES }, allEntries = true) })
	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<SysResourceDTO> permitAnyLeakedResource(SysRoleDTO role) {
		List<SysResource> resources = sysResourceService.listAll();
		List<Long> idList = resources.stream().map(SysResource::getId).collect(Collectors.toList());
		if (!idList.isEmpty()) {
			sysResourceGrantService.setResources(role.getId(), idList);
		}
		return sysResourceService.toDtoList(resources);
	}

}
