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

package com.power4j.ji.common.data.mybatis.handler;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
@MappedTypes(value = { String[].class })
@MappedJdbcTypes(value = JdbcType.VARCHAR)
public class LongArrayTypeHandler extends BaseTypeHandler<Long[]> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Long[] parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, ArrayUtil.join(parameter, StrUtil.COMMA));
	}

	@Override
	@SneakyThrows
	public Long[] getNullableResult(ResultSet rs, String columnName) {
		return strToLongArray(rs.getString(columnName));
	}

	@Override
	@SneakyThrows
	public Long[] getNullableResult(ResultSet rs, int columnIndex) {
		return strToLongArray(rs.getString(columnIndex));
	}

	@Override
	@SneakyThrows
	public Long[] getNullableResult(CallableStatement cs, int columnIndex) {
		return strToLongArray(cs.getString(columnIndex));
	}

	private Long[] strToLongArray(String str) {
		if (CharSequenceUtil.isEmpty(str)) {
			return new Long[0];
		}
		return CharSequenceUtil.split(str, CharUtil.COMMA).stream().map(Long::parseLong).toArray(Long[]::new);
	}

}