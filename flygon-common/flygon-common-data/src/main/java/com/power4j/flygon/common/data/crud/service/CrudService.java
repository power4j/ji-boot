package com.power4j.flygon.common.data.crud.service;

/**
 */

import com.power4j.flygon.common.core.model.PageData;
import com.power4j.flygon.common.core.model.PageRequest;
import com.power4j.flygon.common.data.crud.util.Unique;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/19
 * @since 1.0
 * @param <D> DTO
 * @param <T> Entity
 */
public interface CrudService<D, T extends Unique> extends BaseService<T> {

	/**
	 * DTO 转 Entity
	 * @param dto
	 * @return
	 */
	T toEntity(D dto);

	/**
	 * DTO 转 Entity
	 * @param dtoList
	 * @return
	 */
	default List<T> toEntityList(Collection<D> dtoList) {
		if (dtoList == null || dtoList.isEmpty()) {
			return Collections.emptyList();
		}
		return dtoList.stream().map(o -> toEntity(o)).collect(Collectors.toList());
	}

	/**
	 * Entity 转 DTO
	 * @param entity
	 * @return
	 */
	D toDto(T entity);

	/**
	 * Entity 转 DTO
	 * @param entityList
	 * @return
	 */
	default List<D> toDtoList(Collection<T> entityList) {
		if (entityList == null || entityList.isEmpty()) {
			return Collections.emptyList();
		}
		return entityList.stream().map(o -> toDto(o)).collect(Collectors.toList());
	}

	/**
	 * 分页查询
	 * @param pageRequest
	 * @param queryParam
	 * @return
	 */
	PageData<D> selectPage(PageRequest pageRequest, D queryParam);

	/**
	 * 创建
	 * @param dto
	 * @return
	 */
	D post(D dto);

	/**
	 * 搜索
	 * @param dto
	 * @return
	 */
	List<D> searchList(D dto);

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	Optional<D> read(Serializable id);

	/**
	 * 修改
	 * @param dto
	 * @return
	 */
	D put(D dto);

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	Optional<D> delete(Serializable id);

}
