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

package com.power4j.ji.common.core.util;

import com.power4j.ji.common.core.model.Node;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 树型数据结构工具类
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-17
 * @since 1.0
 */
@UtilityClass
public class TreeUtil {

	// ~ TreeNode 集合处理
	// ---------------------------------------------------------------------------------------------------------------

	/**
	 * 将一维树形节点构建为树
	 * @param nodes 树形节点数据源
	 * @param rootId 根节点ID,将以它为根来构造树
	 * @param <T> Node类型
	 * @return 树形结构
	 */
	public <T extends Node<T>> List<T> buildTree(Collection<T> nodes, final Object rootId) {
		List<T> tree = new ArrayList<>();
		nodes.forEach(node -> {
			if (rootId.equals(node.getNodePid())) {
				tree.add(mountChildren(nodes, node));
			}
		});
		return tree;
	}

	/**
	 * 非递归查找
	 * @param nodes 树形节点数据源
	 * @param predicate 断言
	 * @return List
	 */
	public <T extends Node<T>> List<T> simpleSearch(Collection<T> nodes, Predicate<? super T> predicate) {
		return nodes.stream().filter(predicate).collect(Collectors.toList());
	}

	// ~ 树形结构处理
	// ---------------------------------------------------------------------------------------------------------------

	/**
	 * 遍历并过滤节点
	 * @param nodes 节点
	 * @param predicate 断言,返回true表示该节点需要保留
	 * @return List
	 */
	@Nullable
	public <T extends Node<T>> List<T> filterNode(@Nullable List<T> nodes, Predicate<? super T> predicate) {
		if (nodes == null || nodes.isEmpty()) {
			return nodes;
		}
		List<T> ret = new ArrayList<>();
		nodes.forEach(node -> {
			if (predicate.test(node)) {
				ret.add(node);
				if (node.getNextNodes() != null && !node.getNextNodes().isEmpty()) {
					node.setNextNodes(filterNode(node.getNextNodes(), predicate));
				}
			}
		});
		return ret;
	}

	/**
	 * 遍历并过滤节点
	 * @param root 根节点,即遍历的起始节点
	 * @param predicate 断言,返回true表示该节点需要保留
	 * @param out 集合容器，保存过滤结果
	 * @return 过滤结果
	 */
	public <T extends Node<T>> Collection<T> filter(T root, Predicate<? super T> predicate, Collection<T> out) {
		if (predicate.test(root)) {
			out.add(root);
		}
		if (root.getNextNodes() != null && !root.getNextNodes().isEmpty()) {
			List<T> nodes = root.getNextNodes();
			for (T node : nodes) {
				filter(node, predicate, out);
			}
		}
		return out;
	}

	/**
	 * 遍历并过滤节点
	 * @param nodes 遍历节点
	 * @param predicate 断言
	 * @param out 输出列表
	 * @return 过滤结果
	 */
	public <T extends Node<T>> Collection<? extends T> filter(Collection<T> nodes, Predicate<? super T> predicate,
			Collection<T> out) {
		nodes.forEach(o -> filter(o, predicate, out));
		return out;
	}

	/**
	 * 转化为一维列表
	 * @param root 根节点
	 * @return List
	 */
	public <T extends Node<T>> List<T> flattenTree(T root) {
		List<T> list = new ArrayList<>();
		filter(root, o -> true, list);
		return list;
	}

	/**
	 * 转化为一维列表
	 * @param root 根节点
	 * @return 一维集合
	 */
	public <T extends Node<T>> Collection<T> flattenTree(T root, Collection<T> out) {
		return filter(root, o -> true, out);
	}

	/**
	 * 获取叶子节点
	 * @param root 根节点
	 * @return 节点列表
	 */
	public <T extends Node<T>> Collection<T> getLeafNode(T root) {
		return filter(root, o -> (o.getNextNodes() == null || o.getNextNodes().isEmpty()), new ArrayList<>());
	}

	/**
	 * 递归方式挂载子节点
	 * @param nodes 子节点
	 * @param parent 父节点
	 * @param <T> Node
	 * @return 父节点
	 */
	public <T extends Node<T>> T mountChildren(Collection<T> nodes, T parent) {
		for (T node : nodes) {
			final Long pid = node.getNodePid();
			if (Objects.nonNull(pid) && pid.equals(parent.getNodeId())) {
				if (node.getNextNodes() == null) {
					node.setNextNodes(new ArrayList<>());
				}
				parent.add(mountChildren(nodes, node));
			}
		}
		return parent;
	}

}
