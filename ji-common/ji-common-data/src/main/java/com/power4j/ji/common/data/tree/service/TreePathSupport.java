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

package com.power4j.ji.common.data.tree.service;

import com.power4j.ji.common.core.model.Node;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/17
 * @since 1.0
 */
public interface TreePathSupport<T extends Node<T>> {

	/**
	 * 取下级
	 * @param rootId
	 * @return 如果没有下级返回空列表
	 */
	List<T> getChildren(Long rootId);

	/**
	 * 查询下级节点,并构建为树
	 * @param rootId
	 * @return
	 */
	List<T> getTreeNodes(Long rootId);

	/**
	 * 返回树
	 * @param rootId
	 * @return
	 */
	@Nullable
	T getTree(Long rootId);

}
