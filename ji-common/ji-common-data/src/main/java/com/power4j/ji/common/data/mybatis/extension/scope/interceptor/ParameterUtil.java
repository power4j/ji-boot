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

package com.power4j.ji.common.data.mybatis.extension.scope.interceptor;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.power4j.ji.common.data.mybatis.extension.scope.core.InScope;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/12
 * @since 1.0
 */
@UtilityClass
public class ParameterUtil {

	@SuppressWarnings("unchecked")
	public <T> T cast(@Nullable Object obj, Class<T> targetClass, @Nullable T defVal) {
		if (Objects.nonNull(obj) && obj.getClass().isAssignableFrom(targetClass)) {
			return (T) obj;
		}
		return defVal;
	}

	public <T> T lookup(@Nullable Object obj, Class<T> targetClass, @Nullable T defVal) {
		T found = defVal;
		if (obj instanceof Map) {
			for (Object val : ((Map<?, ?>) obj).values()) {
				if ((found = cast(val, targetClass, null)) != null) {
					break;
				}
			}
		}
		return found;
	}

	public <T> T findParameterByType(Object parameterObject, Class<T> type, @Nullable T defVal) {
		return Optional.ofNullable(ParameterUtil.cast(parameterObject, type, null))
				.orElse(ParameterUtil.lookup(parameterObject, type, defVal));
	}

	public InScope findJoinOn(Object parameterObject) {
		return findParameterByType(parameterObject, InScope.class, null);
	}

	public AbstractWrapper findAbstractWrapper(Object parameterObject) {
		return findParameterByType(parameterObject, AbstractWrapper.class, null);
	}

}
