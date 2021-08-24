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

import com.power4j.ji.admin.modules.sys.component.SysOrgPathBuilder;
import com.power4j.ji.admin.modules.sys.dao.SysOrgMapper;
import com.power4j.ji.admin.modules.sys.dto.SysOrgNodeDTO;
import com.power4j.ji.admin.modules.sys.entity.SysOrg;
import com.power4j.ji.admin.modules.sys.entity.SysOrgNode;
import com.power4j.ji.admin.modules.sys.service.SysOrgService;
import com.power4j.ji.common.data.crud.service.impl.AbstractTreeNodeCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/17
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SysOrgServiceImpl
		extends AbstractTreeNodeCrudService<SysOrgMapper, SysOrgNodeDTO, SysOrg, SysOrgNode, SysOrgPathBuilder>
		implements SysOrgService {

	private final SysOrgPathBuilder pathBuilder;

	@Override
	protected SysOrgPathBuilder getPathBuilder() {
		return pathBuilder;
	}

	@Override
	public List<SysOrg> listAll() {
		return list();
	}

	@Override
	public long countOrgCode(String code, @Nullable Long ignoreId) {
		return countByLambdaColumn(SysOrg::getCode, code, ignoreId);
	}

	@Override
	protected List<SysOrgNodeDTO> fetchChildren(Long rootId, List<SysOrgNodeDTO> defVal) {
		return super.fetchChildren(rootId, defVal).stream().sorted(Comparator.comparingInt(SysOrgNodeDTO::getSort))
				.collect(Collectors.toList());
	}

}
