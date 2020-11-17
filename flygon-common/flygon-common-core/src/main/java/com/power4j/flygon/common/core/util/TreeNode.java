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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 节点
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 11/17/20
 * @since 1.0
 */
@Data
@ApiModel(value = "节点")
public class TreeNode implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "当前节点id")
	protected int id;

	@ApiModelProperty(value = "父节点id")
	protected int parentId;

	@ApiModelProperty(value = "子节点列表")
	protected List<TreeNode> children = new ArrayList<>();

	public void add(TreeNode node) {
		children.add(node);
	}

}