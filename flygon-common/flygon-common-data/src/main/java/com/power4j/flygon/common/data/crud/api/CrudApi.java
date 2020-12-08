package com.power4j.flygon.common.data.crud.api;

import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.validate.Groups;
import com.power4j.flygon.common.data.crud.util.Unique;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
public interface CrudApi<D extends Unique,R extends ApiResponse<D>> {

	/**
	 * 读取
	 * @param id
	 * @return 如果资源不存在返回 null
	 */
	@GetMapping("/{id}")
	@Operation(summary = "读取")
	R read(@PathVariable("id") Serializable id);

	/**
	 * 创建
	 * @param obj
	 * @return
	 */
	@PostMapping
	@Operation(summary = "添加")
	R post(@Validated({ Groups.Create.class}) @RequestBody D obj);

	/**
	 * 修改
	 * @param obj
	 * @return
	 */
	@PutMapping
	@Operation(summary = "修改")
	R put(@Validated(value = { Groups.Update.class}) @RequestBody D obj);

	/**
	 * 删除
	 * @param id
	 * @return 如果资源存在则返回删除之前的值
	 */
	@DeleteMapping("/{id}")
	@Operation(summary = "删除")
	R delete(@PathVariable("id") Serializable id);
}
