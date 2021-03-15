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

package com.power4j.ji.common.data.mybatis.extension.scope.processor;

import com.power4j.ji.common.data.mybatis.extension.scope.core.InScope;
import com.power4j.ji.common.data.mybatis.extension.scope.core.ProcessOpt;
import com.power4j.ji.common.data.mybatis.extension.scope.core.MpQueryContext;
import com.power4j.ji.common.data.mybatis.extension.scope.core.ScopeModel;
import com.power4j.ji.common.data.mybatis.extension.scope.core.ScopeModelUtil;
import com.power4j.ji.common.data.mybatis.extension.scope.exceptions.SkipQueryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

class SimpleQueryProcessorTest {

	final List<ScopeModel> someEmpty = Arrays.asList(
			ScopeModelUtil.onValues("dept_id", new HashSet<>(Arrays.asList(1, 2, 3, 4))),
			ScopeModelUtil.onValues("another_column", Collections.emptySet()));

	final List<ScopeModel> allEmpty = Collections.emptyList();

	@Test
	public void shouldSkipOnAnyEmpty() {

		ProcessOpt processOpt = new ProcessOpt(true, null);
		SimpleQueryProcessor processor = new SimpleQueryProcessor();

		InScope inScope1 = new InScope(processOpt, someEmpty);
		Assertions.assertThrows(SkipQueryException.class, () -> {
			processor.process(new ProcessContext(new MpQueryContext(), inScope1));
		});

		InScope inScope2 = new InScope(processOpt, allEmpty);
		Assertions.assertThrows(SkipQueryException.class, () -> {
			processor.process(new ProcessContext(new MpQueryContext(), inScope2));
		});
	}

}