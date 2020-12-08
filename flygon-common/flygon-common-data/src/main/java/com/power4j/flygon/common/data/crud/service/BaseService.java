package com.power4j.flygon.common.data.crud.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/19
 * @since 1.0
 * @param <T> Entity
 */
public interface BaseService<T> extends IService<T> {

	/**
	 * 根据主键统计
	 * @param id
	 * @return
	 */
	int countById(Serializable id);

	/**
	 * 根据某个字段进行统计
	 * @param column 数据库字段名称
	 * @param value 数据库字段的值
	 * @param ignoreId 排除的ID
	 * @return
	 */
	int countByColumn(String column, Object value, @Nullable Long ignoreId);
}
