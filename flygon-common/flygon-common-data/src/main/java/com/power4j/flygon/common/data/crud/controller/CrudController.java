package com.power4j.flygon.common.data.crud.controller;

import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.exception.BizException;
import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import com.power4j.flygon.common.data.crud.api.CrudApi;
import com.power4j.flygon.common.data.crud.service.CrudService;
import com.power4j.flygon.common.data.crud.util.OperateFlag;
import com.power4j.flygon.common.data.crud.util.Unique;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
public abstract class CrudController<D extends Unique,T extends Unique> implements CrudApi<D, ApiResponse<D>> {
	@Autowired
	protected CrudService<D,T> crudService;

	@Override
	public ApiResponse<D> read(Serializable id) {
		Optional<D> found = crudService.read(id);
		if(found.isPresent()){
			try {
				preReadCheck(found.get());
			}catch (BizException e){
				return ApiResponse.of(e.getCode(),e.getMessage());
			}
		}
		return ApiResponseUtil.ok(found.get());
	}

	@Override
	public ApiResponse<D> post(D obj) {
		try {
			prePostCheck(obj);
		}catch (BizException e){
			return ApiResponse.of(e.getCode(),e.getMessage());
		}
		return ApiResponseUtil.ok(crudService.post(obj));
	}

	@Override
	public ApiResponse<D> put(D obj) {
		try {
			T entry = crudService.getById(obj.getOnlyId());
			prePutCheck(obj,entry);
		}catch (BizException e){
			return ApiResponse.of(e.getCode(),e.getMessage());
		}
		return ApiResponseUtil.ok(crudService.put(obj));
	}

	@Override
	public ApiResponse<D> delete(Serializable id) {
		Optional<D> found = crudService.read(id);
		if(found.isPresent()){
			try {
				preDeleteCheck(found.get(),crudService.getById(id));
			}catch (BizException e){
				return ApiResponse.of(e.getCode(),e.getMessage());
			}
		}
		crudService.delete(id);
		return ApiResponseUtil.ok(found.get());
	}

	protected void checkOpFlagNq(Object obj,int value,String msg){
		if(obj instanceof OperateFlag){
			if(((OperateFlag) obj).getOperateFlag().intValue() == value){
				throw new BizException(SysErrorCodes.E_FORBIDDEN,msg);
			}
		}
	}

	/**
	 * 前置检查
	 * @param obj
	 * @throws BizException 检查不通过抛出 {@code BizException} 异常
	 */
	protected abstract void prePostCheck(D obj) throws BizException;

	/**
	 * 前置检查
	 * @param obj
	 * @param entity 数据库中的当前值
	 * @throws BizException 检查不通过抛出 {@code BizException} 异常
	 */
	protected abstract void prePutCheck(D obj,T entity) throws BizException;

	/**
	 * 前置检查
	 * @param obj
	 * @throws BizException 检查不通过抛出 {@code BizException} 异常
	 */
	protected abstract void preReadCheck(D obj) throws BizException;

	/**
	 * 前置检查
	 * @param obj
	 * @param entity 数据库中的当前值
	 * @throws BizException 检查不通过抛出 {@code BizException} 异常
	 */
	protected abstract void preDeleteCheck(D obj,T entity) throws BizException;
}
