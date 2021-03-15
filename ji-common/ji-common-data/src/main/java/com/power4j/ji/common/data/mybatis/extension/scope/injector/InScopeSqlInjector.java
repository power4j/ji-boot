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

package com.power4j.ji.common.data.mybatis.extension.scope.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power4j.ji.common.data.mybatis.extension.scope.injector.methods.SelectListInScope;
import com.power4j.ji.common.data.mybatis.extension.scope.injector.methods.SelectMapsInScope;
import com.power4j.ji.common.data.mybatis.extension.scope.injector.methods.SelectMapsPageInScope;
import com.power4j.ji.common.data.mybatis.extension.scope.injector.methods.SelectObjsInScope;
import com.power4j.ji.common.data.mybatis.extension.scope.injector.methods.SelectPageInScope;
import com.power4j.ji.common.data.mybatis.extension.scope.mapper.ScopeSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/14
 * @since 1.0
 */
public class InScopeSqlInjector extends DefaultSqlInjector {

	private final static List<AbstractMethod> ADDITIONAL_METHODS = Collections
			.unmodifiableList(Arrays.asList(new SelectListInScope(), new SelectPageInScope(), new SelectMapsInScope(),
					new SelectMapsPageInScope(), new SelectObjsInScope()));

	@Override
	public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
		List<AbstractMethod> methodList = new ArrayList<>();
		if (ScopeSupport.class.isAssignableFrom(mapperClass)) {
			methodList.addAll(ADDITIONAL_METHODS);
		}
		// Defaults
		if (BaseMapper.class.isAssignableFrom(mapperClass)) {
			methodList.addAll(super.getMethodList(mapperClass));
		}
		return Collections.unmodifiableList(methodList);
	}

}
