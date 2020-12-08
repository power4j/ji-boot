package com.power4j.flygon.common.data.crud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power4j.flygon.common.core.model.PageData;
import com.power4j.flygon.common.core.model.PageRequest;
import com.power4j.flygon.common.data.crud.util.CrudUtil;
import com.power4j.flygon.common.data.crud.util.Unique;
import com.power4j.flygon.common.data.crud.service.CrudService;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/20
 * @since 1.0
 */
public abstract class AbstractCrudService<M extends BaseMapper<T>, D, T extends Unique> extends BaseServiceImpl<M, T>
		implements CrudService<D, T> {

	@Getter
	private Class<D> dtoClass = (Class<D>) ReflectionKit.getSuperClassGenericType(getClass(), 1);

	@Getter
	private Class<T> entityClass = (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 2);

	/**
	 * 分页查询 QueryWrapper
	 * @param param
	 * @return
	 */
	protected Wrapper<T> getSearchWrapper(D param) {
		return param == null ? Wrappers.emptyWrapper() : new QueryWrapper<>(toEntity(param));
	}

	public Optional<D> searchOne(Wrapper<T> wrapper) {
		return Optional.ofNullable(getBaseMapper().selectOne(wrapper)).map(this::toDto);
	}

	@Override
	public T toEntity(D dto) {
		if (dto == null) {
			return null;
		}
		return BeanUtil.toBean(dto, entityClass);
	}

	@Override
	public D toDto(T entity) {
		if (entity == null) {
			return null;
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
	public D post(D dto) {
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
	public D put(D dto) {
		T entity = toEntity(dto);
		updateById(toEntity(dto));
		return toDto(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Optional<D> delete(Serializable id) {
		Optional<D> pre = read(id);
		pre.ifPresent(d -> removeById(id));
		return pre;
	}
}
