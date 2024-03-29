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

package com.power4j.ji.common.data.crud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.power4j.ji.common.data.crud.service.BaseService;
import com.power4j.ji.common.data.mybatis.extension.util.LambdaHelper;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

	@SuppressWarnings("unchecked")
	@Getter
	private final Class<T> entityType = (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(),
			BaseServiceImpl.class, 1);

	@Getter
	private final LambdaHelper<T> lambdaHelper = new LambdaHelper<>(entityType);

	@Override
	public long countById(Serializable id) {
		TableInfo tableInfo = TableInfoHelper.getTableInfo(entityType);
		if (tableInfo == null) {
			throw new IllegalStateException(
					String.format("Can not find TableInfo for %s, check mybatis config", entityType.getSimpleName()));
		}
		QueryWrapper<T> wrapper = new QueryWrapper<>();
		wrapper.eq(tableInfo.getKeyColumn(), id);
		return getBaseMapper().selectCount(wrapper);
	}

	@Override
	public long countByLambdaColumn(SFunction<T, ?> colFunc, Object value, @Nullable Long ignoreId) {
		return countByColumn(lambdaHelper.colToStr(colFunc, true), value, ignoreId);
	}

	@Override
	public long countByLambdaColumns(Map<SFunction<T, ?>, Object> columns, @Nullable Long ignoreId) {
		Map<String, Object> parsed = columns.entrySet().stream()
				.collect(Collectors.toMap(kv -> lambdaHelper.colToStr(kv.getKey(), true), Map.Entry::getValue));
		return countByColumns(parsed, ignoreId);
	}

	@Override
	public long countByColumn(String column, Object value, @Nullable Long ignoreId) {
		Map<String, Object> map = new HashMap<>(1);
		map.put(column, value);
		return countByColumns(map, ignoreId);
	}

	@Override
	public long countByColumns(Map<String, Object> columns, @Nullable Long ignoreId) {
		QueryWrapper<T> wrapper = new QueryWrapper<>();
		wrapper.allEq(columns);
		if (ignoreId != null) {
			TableInfo tableInfo = TableInfoHelper.getTableInfo(entityType);
			if (tableInfo == null) {
				throw new IllegalStateException(String.format("Can not find TableInfo for %s, check mybatis config",
						entityType.getSimpleName()));
			}
			wrapper.ne(tableInfo.getKeyColumn(), ignoreId);
		}
		return getBaseMapper().selectCount(wrapper);
	}

}
