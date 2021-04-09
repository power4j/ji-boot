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
import com.power4j.ji.admin.modules.sys.dao.SysPositionMapper;
import com.power4j.ji.admin.modules.sys.dto.SysPositionDTO;
import com.power4j.ji.admin.modules.sys.entity.SysPosition;
import com.power4j.ji.admin.modules.sys.service.SysPositionService;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.exception.BizException;
import com.power4j.ji.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.ji.common.security.util.SecurityUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/4/9
 * @since 1.0
 */
@Service
public class SysPositionServiceImpl extends AbstractCrudService<SysPositionMapper, SysPositionDTO, SysPosition>
		implements SysPositionService {

	@Override
	public int countRoleCode(String code, @Nullable Long ignoreId) {
		return countByLambdaColumn(SysPosition::getCode, code, ignoreId);
	}

	@Override
	public List<SysPositionDTO> listForUser(String username, @Nullable String grantType) {
		// TODO: IMPL
		return Collections.emptyList();
	}

	@Override
	public Optional<SysPositionDTO> readByCode(String code) {
		Wrapper<SysPosition> wrapper = Wrappers.<SysPosition>lambdaQuery().eq(SysPosition::getCode, code);
		return Optional.ofNullable(toDto(getBaseMapper().selectOne(wrapper)));
	}

	@Override
	public SysPositionDTO post(SysPositionDTO dto) {
		return super.post(dto);
	}

	@Override
	public SysPositionDTO put(SysPositionDTO dto) {
		return super.put(dto);
	}

	@Override
	public Optional<SysPositionDTO> delete(Serializable id) {
		return super.delete(id);
	}

	@Override
	protected SysPositionDTO prePostHandle(SysPositionDTO dto) {
		if (countRoleCode(dto.getCode(), null) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT, String.format("%s 不能使用", dto.getCode()));
		}
		dto.setOwner(SecurityUtil.getLoginUserId().orElse(null));
		return super.prePostHandle(dto);
	}

	@Override
	protected SysPositionDTO prePutHandle(SysPositionDTO dto) {
		if (countRoleCode(dto.getCode(), dto.getId()) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT, String.format("%s 不能使用", dto.getCode()));
		}
		return super.prePutHandle(dto);
	}

	@Override
	protected SysPosition preDeleteHandle(Serializable id) {
		return super.preDeleteHandle(id);
	}

}
