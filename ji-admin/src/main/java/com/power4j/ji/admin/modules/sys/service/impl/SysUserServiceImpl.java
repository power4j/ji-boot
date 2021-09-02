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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power4j.ji.admin.modules.sys.constant.CacheConstant;
import com.power4j.ji.admin.modules.sys.dao.SysUserMapper;
import com.power4j.ji.admin.modules.sys.dto.SysUserDTO;
import com.power4j.ji.admin.modules.sys.entity.SysUser;
import com.power4j.ji.admin.modules.sys.service.SysUserService;
import com.power4j.ji.admin.modules.sys.vo.SearchSysUserVO;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.exception.BizException;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.ji.common.data.crud.util.CrudUtil;
import com.power4j.ji.common.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends AbstractCrudService<SysUserMapper, SysUserDTO, SysUser>
		implements SysUserService {

	private final PasswordEncoder passwordEncoder;

	@Cacheable(cacheNames = CacheConstant.Name.USERNAME_TO_USER, key = "#username")
	@Override
	public Optional<SysUser> getByUsername(String username) {
		Wrapper<SysUser> wrapper = new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, username);
		SysUser entity = getBaseMapper().selectOne(wrapper);
		return Optional.ofNullable(entity);
	}

	@Override
	public PageData<SysUserDTO> selectPage(PageRequest pageRequest, SearchSysUserVO param) {
		Wrapper<SysUser> wrapper = new QueryWrapper<>();
		if (param != null) {
			LocalDate start = ArrayUtil.get(param.getCreateIn(), 0);
			LocalDate end = ArrayUtil.get(param.getCreateIn(), 1);
			wrapper = new QueryWrapper<SysUser>().lambda()
					.eq(CharSequenceUtil.isNotBlank(param.getStatus()), SysUser::getStatus, param.getStatus())
					.likeRight(CharSequenceUtil.isNotEmpty(param.getUsername()), SysUser::getUsername,
							param.getUsername())
					.ge(null != start, SysUser::getCreateAt, CrudUtil.dayStart(start))
					.le(null != end, SysUser::getCreateAt, CrudUtil.dayEnd(end));
		}
		Page<SysUser> page = getBaseMapper().selectPage(
				CrudUtil.toPage(pageRequest, Collections.singletonList(new OrderItem("create_at", true))), wrapper);
		return CrudUtil.toPageData(page).map(o -> toDto(o));
	}

	@Override
	public long countUsername(String username, Long ignoreId) {
		return countByLambdaColumn(SysUser::getUsername, username, ignoreId);
	}

	@Override
	public Optional<SysUser> getByUserId(long uid) {
		return Optional.ofNullable(getById(uid));
	}

	@Override
	@Nullable
	public SysUser toEntity(@Nullable SysUserDTO dto) {
		return BeanUtil.toBean(dto, SysUser.class, CopyOptions.create().setIgnoreProperties("slat"));
	}

	@Override
	protected SysUserDTO prePostHandle(SysUserDTO dto) {
		if (countUsername(dto.getUsername(), null) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT, String.format("%s 不能使用", dto.getUsername()));
		}
		dto.setCreateBy(SecurityUtil.getLoginUsername().orElse(null));
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		return super.prePostHandle(dto);
	}

	@Override
	protected SysUserDTO prePutHandle(SysUserDTO dto) {
		if (countUsername(dto.getUsername(), dto.getId()) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT, String.format("%s 不能使用", dto.getUsername()));
		}
		dto.setPassword(null);
		dto.setUpdateBy(SecurityUtil.getLoginUsername().orElse(null));
		if (!CharSequenceUtil.isBlank(dto.getPassword())) {
			dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		}
		return super.prePutHandle(dto);
	}

	@Override
	protected SysUser preDeleteHandle(Serializable id) {
		return super.preDeleteHandle(id);
	}

	@CacheEvict(cacheNames = { CacheConstant.Name.USERNAME_TO_USER }, key = "#dto.username")
	@Override
	public SysUserDTO post(SysUserDTO dto) {
		return super.post(dto);
	}

	@Override
	public Optional<SysUserDTO> read(Serializable id) {
		return super.read(id);
	}

	@CacheEvict(cacheNames = { CacheConstant.Name.USERNAME_TO_USER }, key = "#dto.username")
	@Override
	public SysUserDTO put(SysUserDTO dto) {
		return super.put(dto);
	}

	@CacheEvict(cacheNames = { CacheConstant.Name.USERNAME_TO_USER }, key = "#result.username")
	@Override
	public Optional<SysUserDTO> delete(Serializable id) {
		return super.delete(id);
	}

}
