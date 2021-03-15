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

package com.power4j.ji.common.data.mybatis.extension.scope.injector;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/14
 * @since 1.0
 */
public enum ScopeSupportMethods {

	/**
	 * SELECT_LIST
	 */
	SELECT_LIST("selectListInScope", "查询满足条件所有数据", "<script>%s SELECT %s FROM %s %s %s\n</script>"),
	/**
	 * SELECT_PAGE selectMapsPageInScope selectObjsInScope selectMapsInScope
	 */
	SELECT_PAGE("selectPageInScope", "查询满足条件所有数据（并翻页）", "<script>%s SELECT %s FROM %s %s %s\n</script>"),
	/**
	 * SELECT_MAPS
	 */
	SELECT_MAPS("selectMapsInScope", "查询满足条件所有数据", "<script>%s SELECT %s FROM %s %s %s\n</script>"),
	/**
	 * SELECT_MAPS_PAGE
	 */
	SELECT_MAPS_PAGE("selectMapsPageInScope", "查询满足条件所有数据（并翻页）", "<script>\n %s SELECT %s FROM %s %s %s\n</script>"),
	/**
	 * SELECT_OBJS
	 */
	SELECT_OBJS("selectObjsInScope", "查询满足条件所有数据", "<script>%s SELECT %s FROM %s %s %s\n</script>");

	private final String name;

	private final String desc;

	private final String sql;

	ScopeSupportMethods(String name, String desc, String sql) {
		this.name = name;
		this.desc = desc;
		this.sql = sql;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public String getSql() {
		return sql;
	}

}
