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

package com.power4j.flygon.admin.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.power4j.flygon.admin.modules.sys.constant.CacheConstant;
import com.power4j.flygon.admin.modules.sys.dao.SysDictItemMapper;
import com.power4j.flygon.admin.modules.sys.dao.SysDictMapper;
import com.power4j.flygon.admin.modules.sys.dto.SysDictDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysDict;
import com.power4j.flygon.admin.modules.sys.entity.SysDictItem;
import com.power4j.flygon.admin.modules.sys.service.SysDictService;
import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.exception.BizException;
import com.power4j.flygon.common.data.crud.service.impl.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends AbstractCrudService<SysDictMapper, SysDictDTO, SysDict>
		implements SysDictService {

	private final SysDictItemMapper sysDictItemMapper;

	@Override
	public int countDictCode(String code, Long ignoreId) {
		return countByColumn("code", code, ignoreId);
	}

	@Cacheable(cacheNames = CacheConstant.Name.DICT_CODE_TO_DICT, key = "#code")
	@Override
	public Optional<SysDictDTO> getDict(String code) {
		Wrapper<SysDict> wrapper = Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, code);
		return searchOne(wrapper);
	}

	@CacheEvict(cacheNames = CacheConstant.Name.DICT_CODE_TO_DICT, allEntries = true, condition = "#result != null")
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Optional<SysDictDTO> delete(Serializable id) {
		sysDictItemMapper.delete(Wrappers.<SysDictItem>lambdaQuery().eq(SysDictItem::getDictId, id));
		return super.delete(id);
	}

	@Override
	protected Wrapper<SysDict> getSearchWrapper(SysDictDTO param) {
		if (param == null) {
			return Wrappers.emptyWrapper();
		}
		return Wrappers.<SysDict>lambdaQuery().like(StrUtil.isNotBlank(param.getName()), SysDict::getName,
				param.getName());
	}

	@Override
	protected SysDictDTO prePostHandle(SysDictDTO dto) {
		if (countDictCode(dto.getCode(), null) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT, String.format("%s 不能使用", dto.getCode()));
		}
		return super.prePostHandle(dto);
	}

	@Override
	protected SysDictDTO prePutHandle(SysDictDTO dto) {
		if (countDictCode(dto.getCode(), dto.getId()) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT, String.format("%s 不能使用", dto.getCode()));
		}
		return super.prePutHandle(dto);
	}

	@Override
	protected SysDict preDeleteHandle(Serializable id) {
		return super.preDeleteHandle(id);
	}

	@Override
	public SysDictDTO post(SysDictDTO dto) {
		return super.post(dto);
	}

	@Override
	public Optional<SysDictDTO> read(Serializable id) {
		return super.read(id);
	}

	@CacheEvict(cacheNames = CacheConstant.Name.DICT_CODE_TO_DICT, allEntries = true)
	@Override
	public SysDictDTO put(SysDictDTO dto) {
		return super.put(dto);
	}

}
