package com.power4j.flygon.common.core.constant;

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
	String QRY_PAGE_INDEX = "page";

	/**
	 * 分页查询: 页大小
	 */
	String QRY_PAGE_SIZE = "size";

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
