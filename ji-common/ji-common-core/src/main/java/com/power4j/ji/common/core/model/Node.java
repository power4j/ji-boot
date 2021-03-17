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

package com.power4j.ji.common.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 节点
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-17
 * @since 1.0
 */
public interface Node<T extends Node<T>> extends Unique {

	/**
	 * 节点id
	 * @return
	 */
	@JsonIgnore
	Long getNodeId();

	/**
	 * 设置id
	 * @param id
	 */
	void setNodeId(Long id);

	/**
	 * 父节点id
	 * @return
	 */
	@JsonIgnore
	@Nullable
	Long getNodePid();

	/**
	 * 设置父节点id
	 * @param pid
	 * @return
	 */
	void setNodePid(Long pid);

	/**
	 * 子节点列表
	 * @return
	 */
	@JsonIgnore
	@Nullable
	List<T> getNextNodes();

	/**
	 * 设置子节点
	 * @param children
	 */
	void setNextNodes(List<T> children);

	/**
	 * 是否有更多子节点可以读取,此方法一般用于帮助客户端懒加载
	 * @param val
	 */
	void setHasMoreNodes(boolean val);

	/**
	 * 添加子级
	 * @param node
	 */
	default void add(T node) {
		getNextNodes().add(node);
	}

	/**
	 * 唯一ID
	 * @return
	 */
	@Override
	default Long getOnlyId() {
		return getNodeId();
	}
}