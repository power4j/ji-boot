package com.power4j.flygon.common.data.crud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.exception.BizException;
import com.power4j.flygon.common.data.crud.util.SysCtl;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Predicate;

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


	/**
	 * 根据某些字段进行统计
	 * @param columns  key 是字段名, value 是字段值
	 * @param ignoreId 排除的ID
	 * @return
	 */
	int countByColumns(Map<String,Object> columns, @Nullable Long ignoreId);

	/**
	 * 校验 SysCtl
	 * @param obj
	 * @param predicate 断言条件，如果返回false则抛出异常
	 * @param msg
	 */
	default void checkSysCtl(Object obj, Predicate<SysCtl> predicate, String msg) {
		if (obj instanceof SysCtl) {
			if (!predicate.test((SysCtl) obj)) {
				throw new BizException(SysErrorCodes.E_FORBIDDEN, msg);
			}
		}
	}

	/**
	 * 校验 SysCtl 不是某个值
	 * @param obj
	 * @param failValue
	 * @param msg
	 */
	default void checkSysCtlNot(Object obj, Integer failValue, String msg) {
		checkSysCtl(obj,o -> !o.getCtlFlag().equals(failValue),msg);
	}
}
