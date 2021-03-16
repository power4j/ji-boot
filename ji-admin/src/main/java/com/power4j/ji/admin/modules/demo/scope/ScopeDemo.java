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

package com.power4j.ji.admin.modules.demo.scope;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power4j.ji.admin.modules.sys.dao.SysRoleMapper;
import com.power4j.ji.admin.modules.sys.entity.SysRole;
import com.power4j.ji.common.data.mybatis.extension.scope.core.InScope;
import com.power4j.ji.common.data.mybatis.extension.scope.core.ScopeModelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/15
 * @since 1.0
 */
@Profile("dev")
@Component
@RequiredArgsConstructor
public class ScopeDemo {

	private final SysRoleMapper sysRoleMapper;

	private SysRoleMapper getBaseMapper() {
		return sysRoleMapper;
	}

	@PostConstruct
	public void init() {

		LambdaQueryWrapper<SysRole> lambdaQueryWrapper = new QueryWrapper<SysRole>().lambda().eq(SysRole::getStatus,
				"0");

		InScope inScopeCode = InScope.ofModel(ScopeModelUtil.onValues("code", Arrays.asList("admin", "test")));
		List<SysRole> ret1 = getBaseMapper().selectListInScope(null, inScopeCode);
		System.out.println(ret1);
		List<SysRole> ret11 = getBaseMapper().selectListInScope(lambdaQueryWrapper, inScopeCode);
		System.out.println(ret11);

		InScope inScopeId = InScope.ofModel(ScopeModelUtil.onValues("id", Arrays.asList(1L, 1338178645550809090L)));
		List<SysRole> ret2 = getBaseMapper().selectListInScope(null, inScopeId);
		System.out.println(ret2);

		Page<SysRole> ret21 = getBaseMapper().selectPageInScope(new Page<>(), lambdaQueryWrapper, inScopeId);
		System.out.printf("total = %d , records = %s%n", ret21.getTotal(), ret21.getRecords());

		InScope joinOneEmpty = InScope.ofModel(ScopeModelUtil.onValues("id", Collections.emptyList()));
		List<SysRole> ret3 = getBaseMapper().selectListInScope(null, joinOneEmpty);
		System.out.println(ret3);

		List<SysRole> ret31 = getBaseMapper().selectListInScope(lambdaQueryWrapper, joinOneEmpty);
		System.out.println(ret31);

		InScope inScopeSql = InScope.ofModel(
				ScopeModelUtil.onSubQuery("code", "select code from t_sys_role where code = 'admin' or code = 'test'"));
		List<SysRole> ret4 = getBaseMapper().selectListInScope(null, inScopeSql);
		System.out.println(ret4);

		Page<SysRole> ret41 = getBaseMapper().selectPageInScope(new Page<>(), lambdaQueryWrapper, inScopeSql);
		System.out.printf("total = %d , records = %s%n", ret41.getTotal(), ret41.getRecords());

	}

}
