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

package com.power4j.ji.admin.modules.sys.ds.internal;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.power4j.ji.admin.modules.sys.dao.SysRoleGranteeMapper;
import com.power4j.ji.admin.modules.sys.entity.SysRole;
import com.power4j.ji.admin.modules.sys.entity.SysRoleGrant;
import com.power4j.ji.common.data.mybatis.extension.scope.core.InScope;
import com.power4j.ji.common.data.mybatis.extension.scope.core.ScopeModelUtil;
import com.power4j.ji.common.data.mybatis.extension.util.LambdaHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/16
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class RoleScope {

	private final SysRoleGranteeMapper sysRoleGranteeMapper;

	/**
	 * 用户拥有的角色
	 * @param uid 用户ID
	 * @return InScope 查询条件
	 */
	public InScope forUser(Long uid, @Nullable String grantType) {
		Wrapper<SysRoleGrant> wrapper = new QueryWrapper<SysRoleGrant>().lambda().eq(SysRoleGrant::getUserId, uid)
				.eq(Objects.nonNull(grantType), SysRoleGrant::getGrantType, grantType);
		LambdaHelper<SysRole> lambdaHelper = new LambdaHelper<>(SysRole.class);
		List<Long> values = sysRoleGranteeMapper.selectList(wrapper).stream().map(SysRoleGrant::getRoleId)
				.collect(Collectors.toList());
		return InScope.ofModel(ScopeModelUtil.onValues(lambdaHelper.colToStr(SysRole::getId, true), values));
	}

}
