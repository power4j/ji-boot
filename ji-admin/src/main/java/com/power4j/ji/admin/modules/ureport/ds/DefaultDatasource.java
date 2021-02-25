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

package com.power4j.ji.admin.modules.ureport.ds;

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import com.bstek.ureport.exception.ReportException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/2/12
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class DefaultDatasource implements BuildinDatasource {

	private final DataSource dataSource;

	@Override
	public String name() {
		return "Default";
	}

	@Override
	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		}
		catch (SQLException e) {
			throw new ReportException(e);
		}
	}

}
