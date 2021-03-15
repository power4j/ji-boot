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

package com.power4j.ji.common.data.mybatis.extension.scope.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.power4j.ji.common.data.mybatis.extension.scope.core.InScope;
import com.power4j.ji.common.data.mybatis.extension.scope.core.ScopeConstants;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/11
 * @since 1.0
 */
public interface ScopeSupport<T> extends Mapper<T> {

	/**
	 * 根据 entity 条件，查询全部记录
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 * @param inScope Scope条件
	 * @return 查询结果
	 */
	List<T> selectListInScope(@Nullable @Param(Constants.WRAPPER) Wrapper<T> queryWrapper,
			@Nullable @Param(ScopeConstants.IN_SCOPE) InScope inScope);

	/**
	 * 根据 Wrapper 条件，查询全部记录
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 * @param inScope Scope条件（可以为 null）
	 * @return 查询结果
	 */
	List<Map<String, Object>> selectMapsInScope(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper,
			@Nullable @Param(ScopeConstants.IN_SCOPE) InScope inScope);

	/**
	 * 根据 Wrapper 条件，查询全部记录
	 * <p>
	 * 注意： 只返回第一个字段的值
	 * </p>
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 * @param inScope Scope条件（可以为 null）
	 * @return 查询结果
	 */
	List<Object> selectObjsInScope(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper,
			@Nullable @Param(ScopeConstants.IN_SCOPE) InScope inScope);

	/**
	 * 根据 entity 条件，查询全部记录（并翻页）
	 * @param page 分页查询条件
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 * @param inScope Scope条件（可以为 null）
	 * @return 分页查询结果
	 */
	<E extends IPage<T>> E selectPageInScope(E page, @Nullable @Param(Constants.WRAPPER) Wrapper<T> queryWrapper,
			@Nullable @Param(ScopeConstants.IN_SCOPE) InScope inScope);

	/**
	 * 根据 Wrapper 条件，查询全部记录（并翻页）
	 * @param page 分页查询条件
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 * @param inScope Scope条件（可以为 null）
	 * @return 分页查询结果
	 */
	<E extends IPage<Map<String, Object>>> E selectMapsPageInScope(E page,
			@Param(Constants.WRAPPER) Wrapper<T> queryWrapper,
			@Nullable @Param(ScopeConstants.IN_SCOPE) InScope inScope);

}
