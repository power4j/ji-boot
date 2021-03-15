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

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisMapperAnnotationBuilder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.power4j.ji.common.data.mybatis.extension.scope.injector.InScopeSqlInjector;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.junit.jupiter.api.Test;

class ScopeSupportMapperTest {

	@Test
	void parse() {
		MybatisConfiguration configuration = new MybatisConfiguration();
		GlobalConfigUtils.getGlobalConfig(configuration).setSqlInjector(new InScopeSqlInjector());
		MybatisMapperAnnotationBuilder a = new MybatisMapperAnnotationBuilder(configuration, MixedMapper.class);
		System.out.printf("Parse %s%n", MixedMapper.class.getSimpleName());
		a.parse();
		MybatisMapperAnnotationBuilder b = new MybatisMapperAnnotationBuilder(configuration, SimpleMapper.class);
		System.out.printf("Parse %s%n", SimpleMapper.class.getSimpleName());
		b.parse();
		configuration.getMappedStatement(MixedMapper.class.getName() + ".insert");
	}

	@CacheNamespaceRef(SimpleMapper.class)
	interface MixedMapper extends ScopeSupport<A>, BaseMapper<A> {

	}

	@CacheNamespace
	interface SimpleMapper extends ScopeSupport<B> {

	}

	@Data
	private static class A {

		private Long id;

	}

	@Data
	@EqualsAndHashCode(callSuper = true)
	private static class B extends A {

	}

}