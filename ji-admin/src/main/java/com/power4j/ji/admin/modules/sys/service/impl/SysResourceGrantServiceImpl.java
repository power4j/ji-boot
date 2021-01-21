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

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.power4j.ji.admin.modules.sys.constant.CacheConstant;
import com.power4j.ji.admin.modules.sys.dao.SysResourceGranteeMapper;
import com.power4j.ji.admin.modules.sys.entity.SysResourceGrantee;
import com.power4j.ji.admin.modules.sys.service.SysResourceGrantService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/14
 * @since 1.0
 */
@Service
public class SysResourceGrantServiceImpl extends ServiceImpl<SysResourceGranteeMapper, SysResourceGrantee>
		implements SysResourceGrantService {

	/**
	 * 清除授权
	 * @param roleId
	 * @return
	 */
	protected int removeByRole(Long roleId) {
		Wrapper<SysResourceGrantee> wrapper = Wrappers.<SysResourceGrantee>lambdaQuery()
				.eq(SysResourceGrantee::getRoleId, roleId);
		return getBaseMapper().delete(wrapper);
	}

	/**
	 * 添加授权
	 * @param roleId
	 * @param resourceIds
	 * @return
	 */
	protected List<SysResourceGrantee> add(Long roleId, Collection<Long> resourceIds) {
		if (resourceIds == null || resourceIds.isEmpty()) {
			return Collections.emptyList();
		}
		List<SysResourceGrantee> list = resourceIds.stream()
				.map(o -> new SysResourceGrantee().setResourceId(o).setRoleId(roleId)).collect(Collectors.toList());
		saveBatch(list);
		return list;
	}

	@Caching(evict = { @CacheEvict(cacheNames = { CacheConstant.Name.ROLE_CODES_TO_RESOURCE_TREE }, allEntries = true),
			@CacheEvict(cacheNames = { CacheConstant.Name.ROLE_CODES_TO_RESOURCES }, allEntries = true) })
	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<SysResourceGrantee> setResources(Long roleId, Collection<Long> resourceIds) {
		removeByRole(roleId);
		return add(roleId, resourceIds);
	}

	@Override
	public List<SysResourceGrantee> getByRole(Long roleId) {
		Wrapper<SysResourceGrantee> wrapper = Wrappers.<SysResourceGrantee>lambdaQuery()
				.eq(SysResourceGrantee::getRoleId, roleId);
		return getBaseMapper().selectList(wrapper);
	}

}
