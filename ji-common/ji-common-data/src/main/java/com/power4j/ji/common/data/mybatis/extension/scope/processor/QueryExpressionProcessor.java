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

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.power4j.ji.common.data.mybatis.extension.scope.core.ProcessOpt;
import com.power4j.ji.common.data.mybatis.extension.scope.core.MpQueryContext;
import com.power4j.ji.common.data.mybatis.extension.scope.exceptions.SkipQueryException;
import com.power4j.ji.common.data.mybatis.extension.sql.SqlHelper;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.statement.select.PlainSelect;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/15
 * @since 1.0
 */
public class QueryExpressionProcessor extends AbstractQueryProcessor {

	@Override
	protected void handleValues(MpQueryContext queryContext, ProcessOpt processOpt, Map<String, Set<Object>> colBinding)
			throws SkipQueryException {
		final PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(queryContext.getBoundSql());
		final PlainSelect plainSelect = SqlHelper.getPlainSelect(SqlHelper.getStatement(mpBs.sql()));
		final String tabAlias = Optional.ofNullable(plainSelect.getFromItem().getAlias()).map(Alias::getName)
				.orElse(null);
		Expression additional = SqlHelper.compose(SqlHelper.buildInExpressionForValues(tabAlias, colBinding), true);
		mergeWhereExpression(plainSelect, additional);
		mpBs.sql(plainSelect.toString());
	}

	@Override
	protected void handleSubQuery(MpQueryContext queryContext, ProcessOpt processOpt, Map<String, String> sqlBinding)
			throws SkipQueryException {
		final PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(queryContext.getBoundSql());
		final PlainSelect plainSelect = SqlHelper.getPlainSelect(SqlHelper.getStatement(mpBs.sql()));
		final String tabAlias = Optional.ofNullable(plainSelect.getFromItem().getAlias()).map(Alias::getName)
				.orElse(null);
		Expression additional = SqlHelper.compose(SqlHelper.buildInExpressionForSql(tabAlias, sqlBinding), true);
		mergeWhereExpression(plainSelect, additional);
		mpBs.sql(plainSelect.toString());
	}

	protected void mergeWhereExpression(PlainSelect plainSelect, Expression additional) {
		Expression where = plainSelect.getWhere();
		if (where == null) {
			where = additional;
		}
		if (where instanceof OrExpression) {
			where = new AndExpression(new Parenthesis(where), additional);
		}
		else {
			where = new AndExpression(where, additional);
		}
		plainSelect.setWhere(where);
	}

}
