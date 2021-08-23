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
import com.power4j.ji.common.data.mybatis.extension.scope.core.InScope;
import com.power4j.ji.common.data.mybatis.extension.scope.core.MpQueryContext;
import com.power4j.ji.common.data.mybatis.extension.scope.core.ProcessOpt;
import com.power4j.ji.common.data.mybatis.extension.scope.core.ScopeModel;
import com.power4j.ji.common.data.mybatis.extension.scope.core.ScopeModelUtil;
import com.power4j.ji.common.data.mybatis.extension.scope.exceptions.SkipQueryException;
import com.power4j.ji.common.data.mybatis.extension.scope.exceptions.SqlProcessException;
import net.sf.jsqlparser.JSQLParserException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

class QueryExpressionProcessorTest {

	// @formatter:off

	// 原始未增强的 SQL
	final String sql1 = "SELECT  id,code,name,status,owner,sys_flag,del_flag,create_at,update_at  FROM t_sys_role   WHERE del_flag IS NULL";
	final String sql2 = "SELECT t.*, sys_dept.name FROM t_sys_role t left join sys_dept on sys_dept.id = t.dept_id WHERE t.del_flag IS NULL  order by code ";

	// 关联原始查询的两个列,another_column 关联了空集
	final List<ScopeModel> someEmpty = Arrays.asList(
			ScopeModelUtil.onValues("dept_id", new HashSet<>(Arrays.asList(1, 2, 3, 4))),
			ScopeModelUtil.onValues("another_column", Collections.emptySet())
	);

	final List<ScopeModel> allEmpty = Collections.emptyList();

	// 在 no_matter 列上关联子查询
	final List<ScopeModel> badSql = Collections.singletonList(
			ScopeModelUtil.onSubQuery("no_matter", "select xx form t_xx where uid=1")
	);

	// 混合模式,一个列关联数据集，另一个列关联子查询
	final List<ScopeModel> mixedModels = Arrays.asList(
			ScopeModelUtil.onValues("dept_id", new HashSet<>(Arrays.asList(1, 2, 3, 4))),
			ScopeModelUtil.onSubQuery("another_column", "select xx from t_xx where uid=1")
	);

	// 经过框架处理后,自动添加的条件语句
	final String sql1ProcessedPiece = "AND dept_id IN ('1', '2', '3', '4') AND another_column IN (SELECT xx FROM t_xx WHERE uid = 1)";
	final String sql2ProcessedPiece = "AND t.dept_id IN ('1', '2', '3', '4') AND t.another_column IN (SELECT xx FROM t_xx WHERE uid = 1)";
	// @formatter:on

	private MpQueryContext mockMpQueryContext(Configuration configuration, String sql) {
		BoundSql boundSql = new BoundSql(configuration, sql, new ArrayList<>(), null);
		MpQueryContext context = new MpQueryContext();
		context.setBoundSql(boundSql);
		return context;
	}

	@Test
	public void shouldSkipOnAnyEmpty() {
		Configuration configuration = new Configuration();
		ProcessOpt processOpt = new ProcessOpt(true, null);
		QueryExpressionProcessor processor = new QueryExpressionProcessor();
		InScope inScope1 = new InScope(processOpt, someEmpty);
		Assertions.assertThrows(SkipQueryException.class, () -> {
			processor.process(new ProcessContext(mockMpQueryContext(configuration, sql1), inScope1));
		});

		InScope inScope2 = new InScope(processOpt, allEmpty);
		Assertions.assertThrows(SkipQueryException.class, () -> {
			processor.process(new ProcessContext(mockMpQueryContext(configuration, sql2), inScope2));
		});
	}

	@Test
	public void shouldFailOnBadSql() throws SkipQueryException {
		Configuration configuration = new Configuration();
		ProcessOpt processOpt = new ProcessOpt(true, null);
		QueryExpressionProcessor processor = new QueryExpressionProcessor();
		InScope inScope1 = new InScope(processOpt, badSql);

		try {
			processor.process(new ProcessContext(mockMpQueryContext(configuration, sql1), inScope1));
		}
		catch (SqlProcessException e) {
			Assertions.assertEquals(JSQLParserException.class, e.getCause().getClass());
			System.out.println(e.getCause().getMessage());
			return;
		}
		Assertions.fail("Should throw exception");
	}

	@Test
	public void mixedModelTest() throws SkipQueryException {
		Configuration configuration = new Configuration();
		ProcessOpt processOpt = new ProcessOpt(true, null);
		QueryExpressionProcessor processor = new QueryExpressionProcessor();

		MpQueryContext mpQueryContext1 = mockMpQueryContext(configuration, sql1);
		InScope inScope1 = new InScope(processOpt, mixedModels);
		processor.process(new ProcessContext(mpQueryContext1, inScope1));
		String processedSql1 = mpQueryContext1.getBoundSql().getSql();
		System.out.println(processedSql1);
		Assertions.assertTrue(CharSequenceUtil.containsIgnoreCase(processedSql1, sql1ProcessedPiece));

		MpQueryContext mpQueryContext2 = mockMpQueryContext(configuration, sql2);
		InScope inScope2 = new InScope(processOpt, mixedModels);
		processor.process(new ProcessContext(mpQueryContext2, inScope2));
		String processedSql2 = mpQueryContext2.getBoundSql().getSql();
		System.out.println(processedSql2);
		Assertions.assertTrue(CharSequenceUtil.containsIgnoreCase(processedSql2, sql2ProcessedPiece));
	}

}