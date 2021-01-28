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

package com.power4j.ji.common.core.constant;

/**
 * 数据访问常量
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/22
 * @since 1.0
 */
public interface CrudConstant {

	/**
	 * 分页查询: 页码
	 */
	String QRY_PAGE_INDEX = "current";

	/**
	 * 默认页码
	 */
	int DEFAULT_PAGE_INDEX = 1;

	/**
	 * 分页查询: 页大小
	 */
	String QRY_PAGE_SIZE = "size";

	/**
	 * 默认页大小
	 */
	int DEFAULT_PAGE_SIZE = 20;

	/**
	 * 分页查询: 总条数
	 */
	String QRY_PAGE_TOTAL = "total";

	/**
	 * 分页查询: 数据行
	 */
	String QRY_PAGE_RECORDS = "records";

	/**
	 * 分页查询: 升序排序
	 */
	String QRY_PAGE_ORDER_ASC = "orderAsc";

	/**
	 * 分页查询: 排序字段
	 */
	String QRY_PAGE_ORDER_PROP = "orderProp";

	/**
	 * 分页查询: 升序字段
	 */
	String QRY_PAGE_ASC = "asc";

	/**
	 * 分页查询: 降序字段
	 */
	String QRY_PAGE_DESC = "desc";

	/**
	 * 逻辑删除字段
	 */
	String COLUMN_LOGICAL_DEL = "delFlag";

	/**
	 * 逻辑删除值: 正常
	 */
	int LOGICAL_NOT_DEL = 0;

	/**
	 * 逻辑删除值: 已经删除
	 */
	int LOGICAL_DEL = 1;

	/**
	 * 审计字段: 创建时间
	 */
	String COLUMN_CREATE_AT = "createAt";

	/**
	 * 审计字段: 更新时间
	 */
	String COLUMN_UPDATE_AT = "updateAt";

}
