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

package com.power4j.ji.common.data.crud.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.exception.BizException;
import com.power4j.ji.common.data.crud.util.SysCtl;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/19
 * @since 1.0
 * @param <T> Entity
 */
public interface BaseService<T> {

	/**
	 * 根据主键统计
	 * @param id
	 * @return 记录行数
	 */
	int countById(Serializable id);

	/**
	 * 根据某个字段进行统计
	 * @param colFunc 数据库字段名称
	 * @param value 数据库字段的值
	 * @param ignoreId 排除的ID
	 * @return 记录行数
	 */
	int countByLambdaColumn(SFunction<T, ?> colFunc, Object value, @Nullable Long ignoreId);

	/**
	 * 根据某些字段进行统计
	 * @param columns key 是字段名, value 是字段值
	 * @param ignoreId 排除的ID
	 * @return 记录行数
	 */
	int countByLambdaColumns(Map<SFunction<T, ?>, Object> columns, @Nullable Long ignoreId);

	/**
	 * 根据某个字段进行统计
	 * @param column 数据库字段名称
	 * @param value 数据库字段的值
	 * @param ignoreId 排除的ID
	 * @return 记录行数
	 */
	int countByColumn(String column, Object value, @Nullable Long ignoreId);

	/**
	 * 根据某些字段进行统计
	 * @param columns key 是字段名, value 是字段值
	 * @param ignoreId 排除的ID
	 * @return 记录行数
	 */
	int countByColumns(Map<String, Object> columns, @Nullable Long ignoreId);

	/**
	 * 校验 SysCtl
	 * @param obj
	 * @param predicate 断言条件，如果返回false则抛出异常
	 * @param msg
	 */
	default void checkSysCtl(Object obj, Predicate<SysCtl> predicate, String msg) {
		if (obj instanceof SysCtl) {
			if (!predicate.test((SysCtl) obj)) {
				throw new BizException(SysErrorCodes.E_FORBIDDEN, msg);
			}
		}
	}

	/**
	 * 校验 SysCtl 不是某个值
	 * @param obj
	 * @param failValue
	 * @param msg
	 */
	default void checkSysCtlNot(Object obj, Integer failValue, String msg) {
		checkSysCtl(obj, o -> !o.getLowAttr().equals(failValue), msg);
	}

}
