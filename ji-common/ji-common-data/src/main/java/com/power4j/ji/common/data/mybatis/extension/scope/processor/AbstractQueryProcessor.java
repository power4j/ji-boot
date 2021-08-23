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

import cn.hutool.core.collection.CollectionUtil;
import com.power4j.ji.common.data.mybatis.extension.scope.core.InSubQuery;
import com.power4j.ji.common.data.mybatis.extension.scope.core.InValues;
import com.power4j.ji.common.data.mybatis.extension.scope.core.MpQueryContext;
import com.power4j.ji.common.data.mybatis.extension.scope.core.Pair;
import com.power4j.ji.common.data.mybatis.extension.scope.core.ProcessOpt;
import com.power4j.ji.common.data.mybatis.extension.scope.core.ScopeModel;
import com.power4j.ji.common.data.mybatis.extension.scope.exceptions.SkipQueryException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/15
 * @since 1.0
 */
public abstract class AbstractQueryProcessor implements QueryProcessor {

	@Override
	public void process(ProcessContext context) throws SkipQueryException {
		// @formatter:off
		final ProcessOpt processOpt = context.getInScope().getProcessOpt();
		List<ScopeModel> scopeModels = context.getInScope().getScopeModels();
		if(scopeModels.isEmpty() && processOpt.isSkipEarly()){
			throw new SkipQueryException("No scope offer");
		}
		Map<String, Set<Object>> inValueModels = scopeModels.stream()
				.filter(o -> o  instanceof InValues)
				.map(o -> ((InValues)o).scope())
				.collect(Collectors.toMap(Pair::getKey,Pair::getValue));
		inValueModels = processOpt.isSkipEarly() ? checkSkipEarly(inValueModels) : filterEmpty(inValueModels);

		if(!inValueModels.isEmpty()){
			handleValues(context.getMpQueryContext(), processOpt,inValueModels);
		}

		Map<String, String> inSubQueryModels = scopeModels.stream()
				.filter(o -> o  instanceof InSubQuery)
				.map(o -> ((InSubQuery)o).scope())
				.collect(Collectors.toMap(Pair::getKey,Pair::getValue));
		if(!inSubQueryModels.isEmpty()){
			handleSubQuery(context.getMpQueryContext(),context.getInScope().getProcessOpt(),inSubQueryModels);
		}
		// @formatter:on
	}

	protected Map<String, Set<Object>> filterEmpty(Map<String, Set<Object>> src) {
		return src.entrySet().stream().filter(kv -> CollectionUtil.isNotEmpty(kv.getValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	protected Map<String, Set<Object>> checkSkipEarly(Map<String, Set<Object>> scopes) throws SkipQueryException {
		final int valueModeCount = scopes.size();
		scopes = filterEmpty(scopes);
		if (scopes.size() != valueModeCount) {
			throw new SkipQueryException("Empty scope found");
		}
		return scopes;
	}

	/**
	 * 处理数值集合
	 * @param queryContext
	 * @param processOpt
	 * @param colBinding
	 * @throws SkipQueryException 表示检测到终止查询
	 */
	protected abstract void handleValues(MpQueryContext queryContext, ProcessOpt processOpt,
			Map<String, Set<Object>> colBinding) throws SkipQueryException;

	/**
	 * 处理子查询
	 * @param queryContext
	 * @param processOpt
	 * @param sqlBinding
	 * @throws SkipQueryException 表示检测到终止查询
	 */
	protected abstract void handleSubQuery(MpQueryContext queryContext, ProcessOpt processOpt,
			Map<String, String> sqlBinding) throws SkipQueryException;

}
