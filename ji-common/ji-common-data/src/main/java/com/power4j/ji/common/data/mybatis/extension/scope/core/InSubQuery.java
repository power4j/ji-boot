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

package com.power4j.ji.common.data.mybatis.extension.scope.core;

/**
 * 限定子查询SQL
 *
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/11
 * @since 1.0
 */
public interface InSubQuery extends ScopeModel {

	/**
	 * 子查询语言
	 * @return Key 是连接字段名称,Value 是子查询SQL语言
	 */
	Pair<String, String> scope();

}
