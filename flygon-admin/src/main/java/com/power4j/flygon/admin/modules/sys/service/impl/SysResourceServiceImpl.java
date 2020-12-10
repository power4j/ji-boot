package com.power4j.flygon.admin.modules.sys.service.impl;

import cn.hutool.core.lang.Assert;
import com.power4j.flygon.admin.modules.sys.component.SysResourcePathBuilder;
import com.power4j.flygon.admin.modules.sys.constant.SysConstant;
import com.power4j.flygon.admin.modules.sys.dao.SysResourceMapper;
import com.power4j.flygon.admin.modules.sys.dto.SysResourceDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysResource;
import com.power4j.flygon.admin.modules.sys.entity.SysResourceNode;
import com.power4j.flygon.admin.modules.sys.service.SysResourceService;
import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.exception.BizException;
import com.power4j.flygon.common.core.util.TreeUtil;
import com.power4j.flygon.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.flygon.common.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/30
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SysResourceServiceImpl extends AbstractCrudService<SysResourceMapper, SysResourceDTO, SysResource>
		implements SysResourceService {

	private final SysResourcePathBuilder pathBuilder;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public SysResourceDTO post(SysResourceDTO dto) {
		dto = super.post(dto);
		pathBuilder.insertNode(dto.getParentId(), dto.getId());
		return dto;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysResourceDTO put(SysResourceDTO dto) {
		List<SysResourceNode> nodes = pathBuilder.loadAncestors(dto.getNodeId(), 1, 1);
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

	@Override
	public List<SysResourceDTO> getChildren(Long rootId) {
		List<SysResourceDTO> children = fetchChildren(rootId, Collections.emptyList());
		if(!children.isEmpty()){
			Map<Long,Long> layer = pathBuilder
					.loadDescendants(children.stream().map(SysResourceDTO::getNodeId).collect(Collectors.toList()),1,1)
					.stream()
					.collect(Collectors.toMap(SysResourceNode::getAncestor,SysResourceNode::getDescendant, (v1, v2) -> v2));
			children.forEach(o -> o.setHasChildren(layer.containsKey(o.getNodeId())));
		}

		return children;
	}

	@Override
	public List<SysResourceDTO> getTreeNodes(Long rootId) {
		List<SysResourceDTO> nodes = getChildren(rootId);
		nodes.forEach(node -> buildTree(node));
		return nodes;
	}

	@Override
	public SysResourceDTO getTree(Long rootId) {
		SysResourceDTO root = read(rootId).orElse(null);
		if (root != null) {
			root.setChildren(getTreeNodes(rootId));
		}
		return root;
	}

	@Override
	public List<SysResource> listForRoles(Collection<String> roleCodes) {
		if(roleCodes == null || roleCodes.isEmpty()){
			return Collections.emptyList();
		}
		return getBaseMapper().selectByRoles(roleCodes);
	}

	@Override
	public List<SysResourceDTO> getTreeForRoles(Collection<String> roleCodes) {
		if(roleCodes.isEmpty()){
			return Collections.emptyList();
		}
		Set<Long> granted = listForRoles(SecurityUtil.getLoginUserRoles())
				.stream()
				.map(o -> o.getId())
				.collect(Collectors.toSet());
		List<SysResourceDTO> full = getTreeNodes(SysConstant.ROOT_RESOURCE_ID);
		return TreeUtil.filterNode(full, node -> granted.contains(node.getNodeId()));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Optional<SysResourceDTO> delete(Serializable id) {
		pathBuilder.removeNode(id);
		return super.delete(id);
	}

	protected void buildTree(SysResourceDTO node) {
		node.setNextNodes(getChildren(node.getNodeId()));
		if (node.getNextNodes() != null) {
			node.getNextNodes().forEach(o -> buildTree(o));
		}
	}

	protected List<SysResourceDTO> fetchChildren(Long rootId, List<SysResourceDTO> defVal) {
		List<Long> ids = pathBuilder.loadDescendants(rootId, 1, 1).stream().map(SysResourceNode::getDescendant)
				.collect(Collectors.toList());
		if (ids.isEmpty()) {
			return defVal;
		}
		List<SysResource> children = getBaseMapper().selectBatchIds(ids);
		children.forEach(o -> o.setParentId(rootId));
		return toDtoList(children).stream().sorted(Comparator.comparingInt(SysResourceDTO::getSort)).collect(Collectors.toList());
	}

}
