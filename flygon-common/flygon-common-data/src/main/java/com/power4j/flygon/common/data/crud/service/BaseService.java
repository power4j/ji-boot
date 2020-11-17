package com.power4j.flygon.common.data.crud.service;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/19
 * @since 1.0
 * @param <T> Entity
 */
public interface BaseService<T> extends IService<T> {

	/**
	 * 检查唯一性
	 * @param prop
	 * @param value
	 * @return
	 */
	//int checkUnique(String prop,Object value);

	/**
	 * 检查唯一性
	 * @param prop
	 * @param value
	 * @param excludeId
	 * @return
	 */
	//int checkUnique(String prop, Object value, Serializable excludeId);
}
