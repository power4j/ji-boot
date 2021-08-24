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
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.power4j.ji.admin.modules.sys.dao.SysParamMapper;
import com.power4j.ji.admin.modules.sys.dto.SysParamDTO;
import com.power4j.ji.admin.modules.sys.entity.SysParam;
import com.power4j.ji.admin.modules.sys.service.SysParamService;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.exception.BizException;
import com.power4j.ji.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.ji.common.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/9
 * @since 1.0
 */
@Slf4j
@Service
public class SysParamServiceImpl extends AbstractCrudService<SysParamMapper, SysParamDTO, SysParam>
		implements SysParamService {

	@Override
	public long countParamKey(String key, @Nullable Long ignoreId) {
		return countByLambdaColumn(SysParam::getParamKey, key, ignoreId);
	}

	@Override
	public Optional<SysParamDTO> findByKey(String key) {
		SysParam entity = getBaseMapper().selectOne(Wrappers.<SysParam>lambdaQuery().eq(SysParam::getParamKey, key));
		return Optional.ofNullable(toDto(entity));
	}

	@Override
	protected Wrapper<SysParam> getSearchWrapper(@Nullable SysParamDTO param) {
		if (param == null) {
			return Wrappers.emptyWrapper();
		}
		return Wrappers.<SysParam>lambdaQuery()
				.eq(CharSequenceUtil.isNotBlank(param.getStatus()), SysParam::getStatus, param.getStatus())
				.like(CharSequenceUtil.isNotBlank(param.getParamKey()), SysParam::getParamKey, param.getParamKey());
	}

	@Override
	protected SysParamDTO prePostHandle(SysParamDTO dto) {
		if (countParamKey(dto.getParamKey(), null) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT, String.format("参数名不能使用: %s", dto.getParamKey()));
		}
		dto.setCreateBy(SecurityUtil.getLoginUsername().orElse(null));
		return super.prePostHandle(dto);
	}

	@Override
	protected SysParamDTO prePutHandle(SysParamDTO dto) {
		if (countParamKey(dto.getParamKey(), dto.getId()) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT, String.format("参数名不能使用: %s", dto.getParamKey()));
		}
		dto.setUpdateBy(SecurityUtil.getLoginUsername().orElse(null));
		return super.prePutHandle(dto);
	}

	@Override
	protected SysParam preDeleteHandle(Serializable id) {
		return super.preDeleteHandle(id);
	}

}
