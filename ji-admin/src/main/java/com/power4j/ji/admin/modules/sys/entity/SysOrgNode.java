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

package com.power4j.ji.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.power4j.ji.common.data.tree.entity.TreePath;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/17
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_org_node")
@NoArgsConstructor
public class SysOrgNode extends TreePath implements Serializable {

	private static final long serialVersionUID = 1L;

	public SysOrgNode(Long ancestor, Long descendant, Integer distance) {
		super(ancestor, descendant, distance);
	}

}
