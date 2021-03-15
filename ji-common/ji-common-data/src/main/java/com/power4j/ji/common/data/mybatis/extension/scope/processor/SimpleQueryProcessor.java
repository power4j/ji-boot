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

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.power4j.ji.common.data.mybatis.extension.scope.core.ProcessOpt;
import com.power4j.ji.common.data.mybatis.extension.scope.core.MpQueryContext;
import com.power4j.ji.common.data.mybatis.extension.scope.exceptions.SkipQueryException;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 简单SQL拼接方式
 *
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/12
 * @since 1.0
 */
public class SimpleQueryProcessor extends AbstractQueryProcessor {

	@Override
	protected void handleSubQuery(MpQueryContext queryContext, ProcessOpt processOpt, Map<String, String> sqlBinding)
			throws SkipQueryException {
		final PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(queryContext.getBoundSql());
		final String originalSql = mpBs.sql();
		final String selectWrapper = processOpt.getWrapperName();
		// @formatter:off
		String cond = sqlBinding.entrySet().stream()
				.map(kv -> String.format("%s.%s IN (%s)",selectWrapper,kv.getKey(),kv.getValue()))
				.collect(Collectors.joining(StringPool.AND));
		String sql = String.format("SELECT * FROM ( %s ) %s WHERE %s",
				originalSql,
				selectWrapper,
				cond);
		// @formatter:on
		mpBs.sql(sql);
	}

	@Override
	protected void handleValues(MpQueryContext queryContext, ProcessOpt processOpt, Map<String, Set<Object>> colBinding)
			throws SkipQueryException {
		final PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(queryContext.getBoundSql());
		final String originalSql = mpBs.sql();
		final String selectWrapper = processOpt.getWrapperName();
		// @formatter:off
		Map<String, String> colCond = colBinding.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey,
						kv -> kv.getValue()
								.stream()
								.map(o -> CharSequenceUtil.wrap(o.toString(), StringPool.SINGLE_QUOTE))
								.collect(Collectors.joining(",","(",")"))
				));
		String cond = colCond.entrySet().stream()
				.map(kv -> String.format("%s.%s IN %s", selectWrapper, kv.getKey(), kv.getValue()))
				.collect(Collectors.joining(" AND "));
		String sql = String.format("SELECT * FROM ( %s ) %s WHERE %s",
				originalSql,
				selectWrapper,
				cond);
		// @formatter:on
		mpBs.sql(sql);
	}

}
