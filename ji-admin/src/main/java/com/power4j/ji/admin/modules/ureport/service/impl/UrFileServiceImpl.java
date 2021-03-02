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

package com.power4j.ji.admin.modules.ureport.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power4j.ji.admin.modules.ureport.dao.UrFileMapper;
import com.power4j.ji.admin.modules.ureport.dto.UrFileDTO;
import com.power4j.ji.admin.modules.ureport.entity.UrData;
import com.power4j.ji.admin.modules.ureport.provider.DaoReportProvider;
import com.power4j.ji.admin.modules.ureport.service.UrFileService;
import com.power4j.ji.admin.modules.ureport.vo.SearchUrFileVO;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.ji.common.data.crud.util.CrudUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/2/12
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class UrFileServiceImpl extends AbstractCrudService<UrFileMapper, UrFileDTO, UrData> implements UrFileService {

	private final DaoReportProvider daoReportProvider;

	@Override
	public int countFileName(String file, @Nullable Long ignoreId) {
		return countByColumn("file", file, ignoreId);
	}

	@Override
	public Optional<UrData> findByName(String file) {
		return Optional.ofNullable(getBaseMapper().selectOne(Wrappers.<UrData>lambdaQuery().eq(UrData::getFile, file)));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Optional<UrFileDTO> deleteByFileName(String file) {
		daoReportProvider.deleteReport(file);
		Optional<UrData> urData = findByName(file);
		urData.ifPresent(o -> getBaseMapper().deleteById(o));
		return urData.map(o -> BeanUtil.toBean(o, UrFileDTO.class));
	}

	@Override
	public PageData<UrFileDTO> selectPage(PageRequest pageRequest, @Nullable SearchUrFileVO param) {
		Wrapper<UrData> wrapper = new QueryWrapper<>();
		if (param != null) {
			wrapper = new QueryWrapper<UrData>().lambda().like(CharSequenceUtil.isNotBlank(param.getFile()),
					UrData::getFile, param.getFile());
		}
		Page<UrData> page = getBaseMapper().selectPage(
				CrudUtil.toPage(pageRequest, Collections.singletonList(new OrderItem("create_at", true))), wrapper);
		return CrudUtil.toPageData(page).map(o -> toDto(o));
	}

}
