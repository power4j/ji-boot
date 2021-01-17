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
import lombok.Data;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TreeUtilTest {

	@Data
	public static class TreeNode implements Node<TreeNode> {

		private static final long serialVersionUID = 1L;

		private Long id;

		private Long parentId;

		private List<TreeNode> children = new ArrayList<>();

		@Override
		public Long getNodeId() {
			return id;
		}

		@Override
		public void setNodeId(Long id) {
			this.id = id;
		}

		@Override
		public Long getNodePid() {
			return parentId;
		}

		@Override
		public void setNodePid(Long parentId) {
			this.parentId = parentId;
		}

		@Override
		public List<TreeNode> getNextNodes() {
			return children;
		}

		@Override
		public void setNextNodes(List<TreeNode> children) {
			this.children = children;
		}

	}

	private final Long ROOT_ID = 0L;

	private List<Node> allNode = new ArrayList<>(20);

	private Node createNode(long id, long parentId, List<TreeNode> children) {
		final TreeNode node = new TreeNode();
		node.setNodeId(id);
		node.setNodePid(parentId);
		node.setNextNodes((children == null || children.isEmpty()) ? new ArrayList<>() : children);
		return node;
	}

	@Before
	public void setUp() throws Exception {
		// level 1
		allNode.add(createNode(10001, ROOT_ID, null));
		allNode.add(createNode(20001, ROOT_ID, null));
		// level 2
		allNode.add(createNode(11001, 10001, null));
		allNode.add(createNode(12001, 10001, null));
		allNode.add(createNode(21001, 20001, null));
		allNode.add(createNode(22001, 20001, null));
		// level 3
		allNode.add(createNode(11101, 11001, null));
		allNode.add(createNode(11201, 12001, null));
		allNode.add(createNode(22101, 21001, null));
		allNode.add(createNode(22201, 22001, null));
		// level 4
		allNode.add(createNode(11111, 11101, null));
	}

	@After
	public void tearDown() throws Exception {
		allNode.clear();
	}

	@Test
	public void buildTree() {
		List<Node> fullTree = TreeUtil.buildTree(allNode, ROOT_ID);
		assertEquals(fullTree.size(), 2);

		List<Node> nodeList = new ArrayList<>(10);
		fullTree.forEach(o -> nodeList.addAll(TreeUtil.flattenTree(o)));
		assertEquals(nodeList.size(), allNode.size());

		List<Node> leafList = new ArrayList<>(10);
		fullTree.forEach(o -> leafList.addAll(TreeUtil.getLeafNode(o)));
		assertEquals(leafList.size(), 4);
	}

	@Test
	public void search() {
		List<Node> found = TreeUtil.simpleSearch(allNode, o -> o.getNodePid() == ROOT_ID);
		assertEquals(found.size(), 2);
	}

}