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

package com.power4j.ji.common.data.crud.api;

import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.model.Unique;
import com.power4j.ji.common.core.validate.Groups;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
public interface CrudApi<K, D extends Unique> {

	/**
	 * 读取
	 * @param id
	 * @return 如果资源不存在返回 null
	 */
	@GetMapping("/{id}")
	@Operation(summary = "读取")
	ApiResponse<D> read(@PathVariable("id") K id);

	/**
	 * 读取
	 * @param idList
	 * @return 如果资源不存在返回 null
	 */
	@GetMapping("/list")
	@Operation(summary = "读取列表")
	ApiResponse<List<D>> readList(@RequestParam List<K> idList);

	/**
	 * 创建
	 * @param obj
	 * @return
	 */
	@PostMapping
	@Operation(summary = "添加")
	ApiResponse<D> post(@Validated({ Groups.Default.class, Groups.Create.class }) @RequestBody D obj);

	/**
	 * 修改
	 * @param obj
	 * @return
	 */
	@PutMapping
	@Operation(summary = "修改")
	ApiResponse<D> put(@Validated(value = { Groups.Default.class, Groups.Update.class }) @RequestBody D obj);

	/**
	 * 删除
	 * @param id
	 * @return 如果资源存在则返回删除之前的值
	 */
	@DeleteMapping("/{id}")
	@Operation(summary = "删除")
	ApiResponse<D> delete(@PathVariable("id") K id);

}
