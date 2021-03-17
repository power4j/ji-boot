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

package com.power4j.ji.admin.modules.sys.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power4j.ji.admin.modules.sys.entity.SysOrgNode;
import com.power4j.ji.common.data.tree.util.AbstractTreePathBuilder;
import org.springframework.stereotype.Component;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/17
 * @since 1.0
 */
@Component
public class SysOrgPathBuilder extends AbstractTreePathBuilder<SysOrgNode, BaseMapper<SysOrgNode>> {
	@Override
	protected QueryWrapper<SysOrgNode> createWrapper() {
		return new QueryWrapper<>(null);
	}

	@Override
	protected SysOrgNode createNode(Long ancestor, Long descendant, Integer distance) {
		return new SysOrgNode(ancestor, descendant, distance);
	}
}
