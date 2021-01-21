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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.power4j.ji.admin.modules.sys.constant.CacheConstant;
import com.power4j.ji.admin.modules.sys.dao.SysRoleMapper;
import com.power4j.ji.admin.modules.sys.dao.SysUserMapper;
import com.power4j.ji.admin.modules.sys.dto.SysRoleDTO;
import com.power4j.ji.admin.modules.sys.entity.SysRole;
import com.power4j.ji.admin.modules.sys.entity.SysUser;
import com.power4j.ji.admin.modules.sys.service.SysRoleService;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.exception.BizException;
import com.power4j.ji.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.ji.common.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/10
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends AbstractCrudService<SysRoleMapper, SysRoleDTO, SysRole>
		implements SysRoleService {

	private final SysUserMapper sysUserMapper;

	@Override
	public int countRoleCode(String code, Long ignoreId) {
		return countByColumn("code", code, ignoreId);
	}

	@Cacheable(cacheNames = CacheConstant.Name.USERNAME_TO_ROLES, key = "#username + '_' + #grantType")
	@Override
	public List<SysRole> listForUser(String username, String grantType) {
		SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, username));
		return user == null ? Collections.emptyList() : getBaseMapper().selectByUserId(user.getId(), grantType);
	}

	@Override
	public Optional<SysRoleDTO> readByCode(String code) {
		Wrapper<SysRole> wrapper = Wrappers.<SysRole>lambdaQuery().eq(SysRole::getCode, code);
		return Optional.ofNullable(toDto(getBaseMapper().selectOne(wrapper)));
	}

	@Override
	protected SysRoleDTO prePostHandle(SysRoleDTO dto) {
		if (countRoleCode(dto.getCode(), null) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT, String.format("%s 不能使用", dto.getCode()));
		}
		dto.setOwner(SecurityUtil.getLoginUserId().orElse(null));
		return super.prePostHandle(dto);
	}

	@Override
	protected SysRoleDTO prePutHandle(SysRoleDTO dto) {
		if (countRoleCode(dto.getCode(), dto.getId()) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT, String.format("%s 不能使用", dto.getCode()));
		}
		return super.prePutHandle(dto);
	}

	@Override
	protected SysRole preDeleteHandle(Serializable id) {
		return super.preDeleteHandle(id);
	}

	@Override
	public SysRoleDTO post(SysRoleDTO dto) {
		return super.post(dto);
	}

	@Override
	public Optional<SysRoleDTO> read(Serializable id) {
		return super.read(id);
	}

	@CacheEvict(cacheNames = { CacheConstant.Name.USERNAME_TO_ROLES }, allEntries = true)
	@Override
	public SysRoleDTO put(SysRoleDTO dto) {
		return super.put(dto);
	}

	@CacheEvict(cacheNames = { CacheConstant.Name.USERNAME_TO_ROLES }, allEntries = true, condition = "#result != null")
	@Override
	public Optional<SysRoleDTO> delete(Serializable id) {
		return super.delete(id);
	}

}
