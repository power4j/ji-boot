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

package com.power4j.ji.common.data.crud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.exception.BizException;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.core.model.Unique;
import com.power4j.ji.common.data.crud.constant.LowAttrEnum;
import com.power4j.ji.common.data.crud.service.CrudService;
import com.power4j.ji.common.data.crud.util.CrudUtil;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/20
 * @since 1.0
 */
public abstract class AbstractCrudService<M extends BaseMapper<T>, D extends Unique, T extends Unique>
		extends BaseServiceImpl<M, T> implements CrudService<D, T> {

	@SuppressWarnings("unchecked")
	@Getter
	private final Class<D> dtoClass = (Class<D>) ReflectionKit.getSuperClassGenericType(getClass(),
			AbstractCrudService.class, 1);

	@SuppressWarnings("unchecked")
	@Getter
	private final Class<T> entityType = (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(),
			AbstractCrudService.class, 2);

	/**
	 * 分页查询 QueryWrapper
	 * @param param
	 * @return Wrapper
	 */
	protected Wrapper<T> getSearchWrapper(@Nullable D param) {
		return param == null ? Wrappers.emptyWrapper() : new QueryWrapper<>(toEntity(param));
	}

	public Optional<D> searchOne(@Nullable Wrapper<T> wrapper) {
		return Optional.ofNullable(getBaseMapper().selectOne(wrapper)).map(this::toDto);
	}

	@Override
	@Nullable
	public T toEntity(@Nullable D dto) {
		if (dto == null) {
			return null;
		}
		return BeanUtil.toBean(dto, entityType);
	}

	@Override
	@Nullable
	public D toDto(@Nullable T entity) {
		if (entity == null) {
			return null;
		}
		return BeanUtil.toBean(entity, dtoClass);
	}

	@Override
	public PageData<D> selectPage(PageRequest pageRequest, @Nullable D queryParam) {
		Page<T> page = getBaseMapper().selectPage(CrudUtil.toPage(pageRequest), getSearchWrapper(queryParam));
		return CrudUtil.toPageData(page).map(this::toDto);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public D post(D dto) {
		T entity = toEntity(prePostHandle(dto));
		save(entity);
		return Objects.requireNonNull(toDto(entity));
	}

	@Override
	public List<D> searchList(@Nullable D dto) {
		List<T> list = getBaseMapper().selectList(new QueryWrapper<>(toEntity(dto)));
		return toDtoList(list);
	}

	@Override
	public Optional<D> read(Serializable id) {
		return Optional.ofNullable(toDto(getBaseMapper().selectById(id)));
	}

	@Override
	public <S extends Serializable> List<D> readList(Collection<S> idList) {
		return toDtoList(getBaseMapper().selectBatchIds(idList));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public D put(D dto) {
		updateById(toEntity(prePutHandle(dto)));
		return dto;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Optional<D> delete(Serializable id) {
		T entity = preDeleteHandle(id);
		if (entity != null) {
			removeById(id);
			return Optional.of(toDto(entity));
		}
		return Optional.empty();
	}

	/**
	 * 简单前置检查,并对入参进行处理
	 * @param dto
	 * @return 返回处理后的对象
	 */
	protected D prePostHandle(D dto) {
		return dto;
	}

	/**
	 * 简单前置检查,并对入参进行处理
	 * @param dto
	 * @return 返回处理后的对象
	 */
	protected D prePutHandle(D dto) {
		checkExists(dto.getOnlyId());
		return checkEditable(dto);
	}

	/**
	 * 前置检查
	 * @param id
	 * @return 返回数据库中当前值
	 */
	protected T preDeleteHandle(Serializable id) {
		return checkDeletable(() -> getById(id), null);
	}

	protected T checkExists(Serializable id) {
		return checkExists(() -> getById(id), null);
	}

	protected T checkExists(Supplier<? extends T> supplier, @Nullable String msg) {
		T entity = supplier.get();
		if (entity == null) {
			throw new BizException(SysErrorCodes.E_NOT_FOUND, msg != null ? msg : "资源不存在");
		}
		return entity;
	}

	protected <U> U checkEditable(U data) {
		return checkEditable(() -> data, null);
	}

	protected <U> U checkEditable(Supplier<? extends U> supplier, @Nullable String msg) {
		U data = supplier.get();
		checkSysCtlNot(data, LowAttrEnum.SYS_LOCKED.getValue(), msg != null ? msg : "系统数据不允许修改");
		return data;
	}

	protected <U> U checkDeletable(U data) {
		return checkDeletable(() -> data, null);
	}

	protected <U> U checkDeletable(Supplier<? extends U> supplier, @Nullable String msg) {
		U data = supplier.get();
		checkSysCtlNot(data, LowAttrEnum.SYS_LOCKED.getValue(), msg != null ? msg : "系统数据不允许删除");
		return data;
	}

}
