package com.power4j.flygon.admin.modules.sys.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power4j.flygon.admin.modules.sys.component.ResourcePathBuilder;
import com.power4j.flygon.admin.modules.sys.dto.SysResourceDTO;
import com.power4j.flygon.admin.modules.sys.entity.ResourceNode;
import com.power4j.flygon.admin.modules.sys.entity.SysResource;
import com.power4j.flygon.admin.modules.sys.service.SysResourceService;
import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.exception.BizException;
import com.power4j.flygon.common.data.crud.service.impl.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/30
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SysResourceServiceImpl extends AbstractCrudService<BaseMapper<SysResource>, SysResourceDTO, SysResource> implements SysResourceService {
	private final ResourcePathBuilder pathBuilder;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public SysResourceDTO post(SysResourceDTO dto) {
		dto = super.post(dto);
		pathBuilder.insertNode(dto.getParentId(),dto.getId());
		return dto;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysResourceDTO put(SysResourceDTO dto) {
		List<ResourceNode> nodes = pathBuilder.loadAncestors(dto.getNodeId(),1,1);
		if(nodes.isEmpty()){
			throw new BizException(SysErrorCodes.E_BAD_REQUEST,"无效数据");
		}
		Assert.isTrue(nodes.size() == 1);
		if(!nodes.get(0).getAncestor().equals(dto.getParentId())){
			pathBuilder.removeNode(dto.getId());
			pathBuilder.insertNode(dto.getParentId(),dto.getId());
		}
		return super.put(dto);
	}

	@Override
	public List<SysResourceDTO> getChildren(Long rootId) {
		return getChildren(rootId,Collections.emptyList());
	}

	@Override
	public List<SysResourceDTO> getNodes(Long rootId) {
		List<SysResourceDTO> nodes =  getChildren(rootId);
		nodes.forEach(node -> buildTree(node));
		return nodes;
	}

	@Override
	public SysResourceDTO getTree(Long rootId) {
		SysResourceDTO root = read(rootId).orElse(null);
		if(root != null){
			root.setChildren(getNodes(rootId));
		}
		return root;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Optional<SysResourceDTO> delete(Serializable id) {
		pathBuilder.removeNode(id);
		return super.delete(id);
	}

	protected void buildTree(SysResourceDTO node){
		node.setNextNodes(getChildren(node.getNodeId()));
		if(node.getNextNodes() != null){
			node.getNextNodes().forEach(o -> buildTree(o));
		}
	}

	protected List<SysResourceDTO> getChildren(Long rootId,List<SysResourceDTO> defVal) {
		List<Long> ids = pathBuilder
				.loadDescendants(rootId,1,1)
				.stream()
				.map(ResourceNode::getDescendant)
				.collect(Collectors.toList());
		if(ids.isEmpty()){
			return defVal;
		}
		List<SysResource> children = getBaseMapper().selectBatchIds(ids);
		children.forEach(o -> o.setParentId(rootId));
		return toDtoList(children);
	}
}
