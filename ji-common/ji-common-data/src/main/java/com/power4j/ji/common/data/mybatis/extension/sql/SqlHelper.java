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

package com.power4j.ji.common.data.mybatis.extension.sql;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.power4j.ji.common.data.mybatis.extension.scope.exceptions.SqlProcessException;
import lombok.experimental.UtilityClass;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SubSelect;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/15
 * @since 1.0
 */
@UtilityClass
public class SqlHelper {

	public Statement getStatement(String sql) {
		try {
			return CCJSqlParserUtil.parse(sql);
		}
		catch (JSQLParserException e) {
			throw new SqlProcessException(String.format("Failed to parse SQL, Error SQL: %s", sql), e);
		}
	}

	public SelectBody getSelectBody(Statement statement) {
		if (statement instanceof Select) {
			Select select = (Select) statement;
			return select.getSelectBody();
		}
		throw new SqlProcessException(
				String.format("Could not get SelectBody from : %s", statement.getClass().getName()));
	}

	public PlainSelect getPlainSelect(Statement statement) {
		return (PlainSelect) getSelectBody(statement);
	}

	public Column makeColumn(@Nullable String tabAlias, String columnName) {
		StringBuilder column = new StringBuilder();
		if (tabAlias != null) {
			column.append(tabAlias).append(StringPool.DOT);
		}
		column.append(columnName);
		return new Column(column.toString());
	}

	public List<InExpression> buildInExpressionForValues(String tabAlias, Map<String, Set<Object>> colBinding) {
		return colBinding.entrySet().stream().map(kv -> buildInExpression(tabAlias, kv.getKey(), kv.getValue()))
				.collect(Collectors.toList());
	}

	public InExpression buildInExpression(String tabAlias, String column, Collection<Object> values) {
		// TODO: 改进类型处理
		List<Expression> valueList = values.stream().map(o -> new StringValue(o.toString()))
				.collect(Collectors.toList());
		InExpression inExpression = new InExpression();
		inExpression.setLeftExpression(makeColumn(tabAlias, column));
		inExpression.setRightItemsList(new ExpressionList(valueList));
		return inExpression;
	}

	public List<InExpression> buildInExpressionForSql(String tabAlias, Map<String, String> sqlBinding) {
		return sqlBinding.entrySet().stream().map(kv -> buildInExpression(tabAlias, kv.getKey(), kv.getValue()))
				.collect(Collectors.toList());
	}

	public InExpression buildInExpression(String tabAlias, String column, String sql) {
		SubSelect subSelect = new SubSelect();
		subSelect.setSelectBody(getSelectBody(getStatement(sql)));
		InExpression inExpression = new InExpression();
		inExpression.setLeftExpression(makeColumn(tabAlias, column));
		inExpression.setRightExpression(subSelect);
		return inExpression;
	}

	public Expression compose(Collection<? extends Expression> expressions, boolean andExpr) {
		Assert.notEmpty(expressions, "Must non empty");
		Expression head = null;
		for (Expression expression : expressions) {
			if (Objects.isNull(head)) {
				head = expression;
			}
			else {
				head = andExpr ? new AndExpression(head, expression) : new OrExpression(head,expression);
			}
		}
		return head;
	}

}
