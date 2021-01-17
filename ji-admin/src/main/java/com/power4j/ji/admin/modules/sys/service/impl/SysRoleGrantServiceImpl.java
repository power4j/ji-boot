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

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.power4j.ji.admin.modules.sys.constant.CacheConstant;
import com.power4j.ji.admin.modules.sys.constant.DictConstant;
import com.power4j.ji.admin.modules.sys.dao.SysRoleGranteeMapper;
import com.power4j.ji.admin.modules.sys.dto.SysRoleGrantDTO;
import com.power4j.ji.admin.modules.sys.entity.SysRoleGrant;
import com.power4j.ji.admin.modules.sys.service.SysRoleGrantService;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.exception.BizException;
import com.power4j.ji.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.ji.common.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/14
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SysRoleGrantServiceImpl extends AbstractCrudService<SysRoleGranteeMapper, SysRoleGrantDTO, SysRoleGrant>
		implements SysRoleGrantService {

	@CacheEvict(cacheNames = { CacheConstant.Name.USERNAME_TO_ROLES }, allEntries = true)
	@Override
	public int removeByUser(Long userId) {
		Wrapper<SysRoleGrant> wrapper = Wrappers.<SysRoleGrant>lambdaQuery().eq(SysRoleGrant::getUserId, userId);
		return getBaseMapper().delete(wrapper);
	}

	@Override
	public List<SysRoleGrant> getByUser(Long userId, String grantType) {
		Wrapper<SysRoleGrant> wrapper = Wrappers.<SysRoleGrant>lambdaQuery().eq(SysRoleGrant::getUserId, userId)
				.eq(StrUtil.isNotEmpty(grantType), SysRoleGrant::getGrantType, grantType);
		return list(wrapper);
	}

	@Override
	protected SysRoleGrantDTO prePostHandle(SysRoleGrantDTO dto) throws BizException {
		grantCheck(dto);
		return super.prePostHandle(dto);
	}

	@Override
	protected SysRoleGrantDTO prePutHandle(SysRoleGrantDTO dto) {
		grantCheck(dto);
		return super.prePutHandle(dto);
	}

	@Override
	protected SysRoleGrant preDeleteHandle(Serializable id) {
		return super.preDeleteHandle(id);
	}

	protected void grantCheck(SysRoleGrantDTO obj) {
		// 没有检查授权用户,角色是否存在
		if (SecurityUtil.getLoginUserId().get().equals(obj.getUserId())) {
			throw new BizException(SysErrorCodes.E_FORBIDDEN, "不能修改自己的权限");
		}
		long count = getByUser(obj.getUserId(), DictConstant.Role.GRANT_TYPE_PERMITTED).stream()
				.filter(o -> o.getRoleId().equals(obj.getRoleId())).count();
		if (count <= 0) {
			throw new BizException(SysErrorCodes.E_FORBIDDEN, String.format("无权限对角色%s进行授权", obj.getRoleId()));
		}
	}

	@CacheEvict(cacheNames = { CacheConstant.Name.USERNAME_TO_ROLES }, allEntries = true)
	@Override
	public SysRoleGrantDTO post(SysRoleGrantDTO dto) {
		return super.post(dto);
	}

	@CacheEvict(cacheNames = { CacheConstant.Name.USERNAME_TO_ROLES }, allEntries = true)
	@Override
	public SysRoleGrantDTO put(SysRoleGrantDTO dto) {
		return super.put(dto);
	}

	@CacheEvict(cacheNames = { CacheConstant.Name.USERNAME_TO_ROLES }, allEntries = true)
	@Override
	public Optional<SysRoleGrantDTO> delete(Serializable id) {
		return super.delete(id);
	}

}
