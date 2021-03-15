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

import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.power4j.ji.common.data.mybatis.extension.scope.core.InScope;
import com.power4j.ji.common.data.mybatis.extension.scope.core.MpQueryContext;
import com.power4j.ji.common.data.mybatis.extension.scope.processor.ProcessContext;
import com.power4j.ji.common.data.mybatis.extension.scope.processor.QueryExpressionProcessor;
import com.power4j.ji.common.data.mybatis.extension.scope.processor.QueryProcessor;
import com.power4j.ji.common.data.mybatis.extension.scope.exceptions.SqlProcessException;
import com.power4j.ji.common.data.mybatis.extension.scope.exceptions.SkipQueryException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/12
 * @since 1.0
 */
@Slf4j
public class InScopeInterceptor implements InnerInterceptor {

	private QueryProcessor queryProcessor = new QueryExpressionProcessor();

	public QueryProcessor getQuerySqlPreProcessor() {
		return queryProcessor;
	}

	public void setQuerySqlPreProcessor(QueryProcessor queryProcessor) {
		this.queryProcessor = queryProcessor;
	}

	@Override
	public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
			ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
		boolean doQuery = true;
		final InScope inScope = ParameterUtil.findJoinOn(boundSql.getParameterObject());
		if (inScope != null) {
			ProcessContext processContext = createPreProcessorContext(inScope, executor, ms, parameter, rowBounds,
					resultHandler, boundSql);
			try {
				getQuerySqlPreProcessor().process(processContext);
			}
			catch (SkipQueryException e) {
				log.debug("Skip do query due to SkipQueryException : " + e.getMessage());
				doQuery = false;
			}
		}
		return doQuery;
	}

	@Override
	public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
			ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
		final InScope inScope = ParameterUtil.findJoinOn(boundSql.getParameterObject());
		if (inScope != null) {
			ProcessContext processContext = createPreProcessorContext(inScope, executor, ms, parameter, rowBounds,
					resultHandler, boundSql);
			try {
				getQuerySqlPreProcessor().process(processContext);
			}
			catch (SkipQueryException e) {
				throw new SqlProcessException("Illegal state,should not happen", e);
			}
		}
	}

	public ProcessContext createPreProcessorContext(InScope inScope, Executor executor, MappedStatement ms,
			Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
		MpQueryContext queryContext = new MpQueryContext(executor, ms, parameter, rowBounds, resultHandler, boundSql);
		return new ProcessContext(queryContext, inScope);
	}

}
