package com.power4j.flygon.common.data.crud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power4j.flygon.common.core.model.PageData;
import com.power4j.flygon.common.core.model.PageRequest;
import com.power4j.flygon.common.data.crud.CrudUtil;
import com.power4j.flygon.common.data.crud.service.CrudService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/20
 * @since 1.0
 */
public abstract class AbstractCrudService<M extends BaseMapper<T>, D, T> extends BaseServiceImpl<M, T>
		implements CrudService<D, T> {

	private Class<D> dtoClass;

	private Class<T> entityClass;

	/**
	 * 分页查询 QueryWrapper
	 * @param param
	 * @return
	 */
	protected Wrapper<T> getSearchWrapper(D param) {
		return new QueryWrapper<>(toEntity(param));
	}

	@Override
	public T toEntity(D dto) {
		if (dto == null) {
			return null;
		}
		if (entityClass == null) {
			entityClass = (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 2);
		}
		return BeanUtil.toBean(dto, entityClass);
	}

	@Override
	public D toDto(T entity) {
		if (entity == null) {
			return null;
		}
		if (dtoClass == null) {
			dtoClass = (Class<D>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
		}
		return BeanUtil.toBean(entity, dtoClass);
	}

	@Override
	public PageData<D> selectPage(PageRequest pageRequest, D queryParam) {
		Page<T> page = getBaseMapper().selectPage(CrudUtil.toPage(pageRequest), getSearchWrapper(queryParam));
		return CrudUtil.toPageData(page).map(o -> toDto(o));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public D create(D dto) {
		T entity = toEntity(dto);
		save(entity);
		return toDto(entity);
	}

	@Override
	public List<D> searchList(D dto) {
		List<T> list = getBaseMapper().selectList(new QueryWrapper<>(toEntity(dto)));
		return toDtoList(list);
	}

	@Override
	public Optional<D> read(Serializable id) {
		return Optional.ofNullable(toDto(getBaseMapper().selectById(id)));
	}

	@Override
	public boolean update(D dto) {
		return updateById(toEntity(dto));
	}
}
