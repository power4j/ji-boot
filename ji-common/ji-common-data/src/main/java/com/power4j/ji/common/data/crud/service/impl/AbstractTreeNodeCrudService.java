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

package com.power4j.ji.common.data.crud.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.exception.BizException;
import com.power4j.ji.common.core.model.Node;
import com.power4j.ji.common.core.model.Unique;
import com.power4j.ji.common.data.tree.entity.TreePath;
import com.power4j.ji.common.data.tree.service.TreePathSupport;
import com.power4j.ji.common.data.tree.util.AbstractTreePathBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/17
 * @since 1.0
 * @param <M> 业务对象Mapper 类型
 * @param <D> DTO 类型
 * @param <T> 业务对象类型
 * @param <P> 节点数据类型
 * @param <B> AbstractTreePathBuilder 类型
 */
public abstract class AbstractTreeNodeCrudService<M extends BaseMapper<T>, D extends Node<D>, T extends Unique, P extends TreePath, B extends AbstractTreePathBuilder<P, BaseMapper<P>>>
		extends AbstractCrudService<M, D, T> implements TreePathSupport<D> {

	/**
	 * 获取PathBuilder
	 * @return AbstractTreePathBuilder Object
	 */
	protected abstract B getPathBuilder();

	// ~ 树节点维护
	// ===================================================================================================

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<D> getChildren(Long rootId) {
		List<D> children = fetchChildren(rootId, Collections.emptyList());
		if (!children.isEmpty()) {
			Map<Long, Long> layer = getPathBuilder()
					.loadDescendants(children.stream().map(D::getNodeId).collect(Collectors.toList()), 1, 1).stream()
					.collect(Collectors.toMap(P::getAncestor, P::getDescendant, (v1, v2) -> v2));
			children.forEach(o -> o.setHasMoreNodes(layer.containsKey(o.getNodeId())));
		}

		return children;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<D> getTreeNodes(Long rootId) {
		List<D> nodes = getChildren(rootId);
		nodes.forEach(this::buildTree);
		return nodes;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public D getTree(Long rootId) {
		D root = read(rootId).orElse(null);
		if (root != null) {
			root.setNextNodes(getTreeNodes(rootId));
		}
		return root;
	}

	protected void buildTree(D node) {
		node.setNextNodes(getChildren(node.getNodeId()));
		if (node.getNextNodes() != null) {
			node.getNextNodes().forEach(this::buildTree);
		}
	}

	protected List<D> fetchChildren(Long rootId, List<D> defVal) {
		List<Long> ids = getPathBuilder().loadDescendants(rootId, 1, 1).stream().map(P::getDescendant)
				.collect(Collectors.toList());
		if (ids.isEmpty()) {
			return defVal;
		}
		List<D> children = readList(ids);
		children.forEach(o -> o.setNodePid(rootId));
		return children;
	}

	// ~ 业务对象处理
	// ===================================================================================================

	@Transactional(rollbackFor = Exception.class)
	@Override
	public D post(D dto) {
		dto = super.post(dto);
		getPathBuilder().insertNode(dto.getNodePid(), dto.getOnlyId());
		return dto;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public D put(D dto) {
		B pathBuilder = getPathBuilder();
		List<P> nodes = pathBuilder.loadAncestors(dto.getNodeId(), 1, 1);
		if (nodes.isEmpty()) {
			throw new BizException(SysErrorCodes.E_BAD_REQUEST, "父级不存在");
		}
		Assert.isTrue(nodes.size() == 1);
		if (!nodes.get(0).getAncestor().equals(dto.getNodePid())) {
			pathBuilder.removeNode(dto.getOnlyId());
			pathBuilder.insertNode(dto.getNodePid(), dto.getOnlyId());
		}
		return super.put(dto);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Optional<D> delete(Serializable id) {
		getPathBuilder().removeNode(id);
		return super.delete(id);
	}

}
