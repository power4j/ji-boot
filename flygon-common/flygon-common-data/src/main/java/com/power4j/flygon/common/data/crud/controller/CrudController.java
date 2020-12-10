package com.power4j.flygon.common.data.crud.controller;

import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import com.power4j.flygon.common.data.crud.api.CrudApi;
import com.power4j.flygon.common.data.crud.service.CrudService;
import com.power4j.flygon.common.data.crud.util.Unique;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
public abstract class CrudController<D extends Unique, T extends Unique> implements CrudApi<D> {

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	protected CrudService<D, T> crudService;

	@Override
	public ApiResponse<List<D>> readList(List<Serializable> idList) {
		return ApiResponseUtil.ok(crudService.readList(idList));
	}

	@Override
	public ApiResponse<D> read(Serializable id) {
		return ApiResponseUtil.ok(crudService.read(id).orElse(null));
	}

	@Override
	public ApiResponse<D> post(D obj) {
		return ApiResponseUtil.ok(crudService.post(obj));
	}

	@Override
	public ApiResponse<D> put(D obj) {
		return ApiResponseUtil.ok(crudService.put(obj));
	}

	@Override
	public ApiResponse<D> delete(Serializable id) {
		return ApiResponseUtil.ok(crudService.delete(id).orElse(null));
	}
}
