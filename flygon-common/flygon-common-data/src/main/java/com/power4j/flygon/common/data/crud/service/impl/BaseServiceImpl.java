package com.power4j.flygon.common.data.crud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.power4j.flygon.common.data.crud.service.BaseService;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

	@Getter
	private final Class<T> entityClass = (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 2);

	@Override
	public int countById(Serializable id) {
		TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
		if (tableInfo == null) {
			throw new IllegalStateException(String.format("Can not find TableInfo for %s, check mybatis config",entityClass.getSimpleName()));
		}
		QueryWrapper<T> wrapper = new QueryWrapper<>();
		wrapper.eq(tableInfo.getKeyColumn(), id);
		return getBaseMapper().selectCount(wrapper);
	}

	@Override
	public int countByColumn(String column, Object value, Long ignoreId) {
		Map<String, Object> map = new HashMap<>(1);
		map.put(column,value);
		return countByColumns(map,ignoreId);
	}

	@Override
	public int countByColumns(Map<String, Object> columns, Long ignoreId) {
		QueryWrapper<T> wrapper = new QueryWrapper<>();
		wrapper.allEq(columns);
		if (ignoreId != null) {
			TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
			if (tableInfo == null) {
				throw new IllegalStateException(String.format("Can not find TableInfo for %s, check mybatis config",entityClass.getSimpleName()));
			}
			wrapper.ne(tableInfo.getKeyColumn(), ignoreId);
		}
		return getBaseMapper().selectCount(wrapper);
	}

}
