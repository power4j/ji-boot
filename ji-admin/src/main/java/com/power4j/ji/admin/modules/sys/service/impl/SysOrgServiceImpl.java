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

import cn.hutool.core.lang.Assert;
import com.power4j.ji.admin.modules.sys.component.SysOrgPathBuilder;
import com.power4j.ji.admin.modules.sys.dao.SysOrgMapper;
import com.power4j.ji.admin.modules.sys.dto.SysOrgNodeDTO;
import com.power4j.ji.admin.modules.sys.entity.SysOrg;
import com.power4j.ji.admin.modules.sys.entity.SysOrgNode;
import com.power4j.ji.admin.modules.sys.service.SysOrgService;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.exception.BizException;
import com.power4j.ji.common.data.crud.service.impl.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/17
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SysOrgServiceImpl extends AbstractCrudService<SysOrgMapper, SysOrgNodeDTO, SysOrg> implements SysOrgService {

	private final SysOrgPathBuilder pathBuilder;

	@Override
	public List<SysOrgNodeDTO> getChildren(Long rootId) {
		return null;
	}

	@Override
	public List<SysOrgNodeDTO> getTreeNodes(Long rootId) {
		return null;
	}

	@Override
	public SysOrgNodeDTO getTree(Long rootId) {
		return null;
	}

	@Override
	public List<SysOrg> listAll() {
		return null;
	}

	@Override
	public int countOrgCode(String code, @Nullable Long ignoreId) {
		return 0;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public SysOrgNodeDTO post(SysOrgNodeDTO dto) {
		dto = super.post(dto);
		pathBuilder.insertNode(dto.getParentId(), dto.getId());
		return dto;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public SysOrgNodeDTO put(SysOrgNodeDTO dto) {
		List<SysOrgNode> nodes = pathBuilder.loadAncestors(dto.getNodeId(), 1, 1);
		if (nodes.isEmpty()) {
			throw new BizException(SysErrorCodes.E_BAD_REQUEST, "无效数据");
		}
		Assert.isTrue(nodes.size() == 1);
		if (!nodes.get(0).getAncestor().equals(dto.getParentId())) {
			pathBuilder.removeNode(dto.getId());
			pathBuilder.insertNode(dto.getParentId(), dto.getId());
		}
		return super.put(dto);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Optional<SysOrgNodeDTO> delete(Serializable id) {
		pathBuilder.removeNode(id);
		return super.delete(id);
	}
}
