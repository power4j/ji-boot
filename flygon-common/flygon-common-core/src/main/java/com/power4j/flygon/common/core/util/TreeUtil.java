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

package com.power4j.flygon.common.core.util;

import com.power4j.flygon.common.core.model.TreeNode;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
	 * @param <T>
	 * @return
	 */
	public <T extends TreeNode> List<T> buildTree(Collection<T> nodes, final Object rootId) {
		List<T> tree = new ArrayList<>();
		nodes.forEach(node -> {
			if (rootId.equals(node.getParentId())) {
				tree.add(mountChildren(nodes, node));
			}
		});
		return tree;
	}

	/**
	 * 非递归查找
	 * @param nodes 树形节点数据源
	 * @param predicate
	 * @return
	 */
	public List<TreeNode> simpleSearch(Collection<? extends TreeNode> nodes,
			final Predicate<? super TreeNode> predicate) {
		return nodes.stream().filter(predicate::test).collect(Collectors.toList());
	}

	// ~ 树形结构处理
	// ---------------------------------------------------------------------------------------------------------------

	/**
	 * 遍历并收集节点
	 * @param root 根节点,即遍历的起始节点
	 * @param predicate 断言,返回true表示该节点需要被收集
	 * @param out 集合容器，保存收集结果
	 * @return
	 */
	public Collection<TreeNode> findChildren(TreeNode root, final Predicate<? super TreeNode> predicate,
			Collection<TreeNode> out) {
		if (predicate.test(root)) {
			out.add(root);
		}
		if (root.getChildren() != null && !root.getChildren().isEmpty()) {
			for (TreeNode node : root.getChildren()) {
				findChildren(node, predicate, out);
			}
		}
		return out;
	}

	/**
	 * 遍历并收集节点
	 * @param nodes
	 * @param predicate
	 * @param out
	 * @return
	 */
	public Collection<TreeNode> findChildren(Collection<TreeNode> nodes, final Predicate<? super TreeNode> predicate,
			Collection<TreeNode> out) {
		nodes.forEach(o -> findChildren(o, predicate, out));
		return out;
	}

	/**
	 * 转化为一维列表
	 * @param root
	 * @return
	 */
	public List<? extends TreeNode> flattenTree(TreeNode root) {
		List<TreeNode> list = new ArrayList<>();
		findChildren(root, o -> true, list);
		return list;
	}

	/**
	 * 转化为一维列表
	 * @param root
	 * @return
	 */
	public Collection<? extends TreeNode> flattenTree(TreeNode root, Collection<TreeNode> out) {
		return findChildren(root, o -> true, out);
	}

	/**
	 * 获取叶子节点
	 * @param root
	 * @return
	 */
	public Collection<? extends TreeNode> getLeafNode(TreeNode root) {
		return findChildren(root, o -> (o.getChildren() == null || o.getChildren().isEmpty()), new ArrayList<>());
	}

	/**
	 * 递归方式挂载子节点
	 * @param nodes
	 * @param parent
	 * @param <T>
	 * @return
	 */
	public <T extends TreeNode> T mountChildren(Collection<T> nodes, T parent) {
		for (T node : nodes) {
			if (node.getParentId() == parent.getId()) {
				if (node.getChildren() == null) {
					node.setChildren(new ArrayList<>());
				}
				parent.add(mountChildren(nodes, node));
			}
		}
		return parent;
	}

}
