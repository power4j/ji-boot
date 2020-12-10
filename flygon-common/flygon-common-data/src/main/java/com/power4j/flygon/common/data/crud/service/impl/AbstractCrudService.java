package com.power4j.flygon.common.data.crud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.exception.BizException;
import com.power4j.flygon.common.core.model.PageData;
import com.power4j.flygon.common.core.model.PageRequest;
import com.power4j.flygon.common.data.crud.constant.SysCtlFlagEnum;
import com.power4j.flygon.common.data.crud.service.CrudService;
import com.power4j.flygon.common.data.crud.util.CrudUtil;
import com.power4j.flygon.common.data.crud.util.Unique;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/20
 * @since 1.0
 */
public abstract class AbstractCrudService<M extends BaseMapper<T>, D extends Unique, T extends Unique> extends BaseServiceImpl<M, T>
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
	@Transactional(rollbackFor = Exception.class)
	public D put(D dto) {
		updateById(toEntity(prePutHandle(dto)));
		return dto;
	}

	@Override
	public List<D> readList(Collection<Serializable> idList) {
		return toDtoList(getBaseMapper().selectBatchIds(idList));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Optional<D> delete(Serializable id) {
		T entity = preDeleteHandle(id);
		if(entity != null){
			removeById(id);
			return Optional.of(toDto(entity));
		}
		return Optional.empty();
	}

	/**
	 * 前置检查,并对入参进行处理
	 * @param dto
	 * @return 返回处理后的对象
	 */
	protected D prePostHandle(D dto){
		return dto;
	}

	/**
	 * 前置检查,并对入参进行处理
	 * @param dto
	 * @return 返回处理后的对象
	 */
	protected D prePutHandle(D dto){
		T entity = getById(dto.getOnlyId());
		if (entity == null) {
			throw new BizException(SysErrorCodes.E_CONFLICT, String.format("数据不存在"));
		}
		checkSysCtlNot(entity,SysCtlFlagEnum.SYS_LOCKED.getValue(),"系统数据不允许修改");
		return dto;
	}

	/**
	 * 前置检查
	 * @param id
	 * @return 返回数据库中当前值
	 */
	protected T preDeleteHandle(Serializable id){
		T entity = getById(id);
		if(entity != null){
			checkSysCtlNot(entity,SysCtlFlagEnum.SYS_LOCKED.getValue(),"系统数据不允许删除");
		}
		return entity;
	}
}
