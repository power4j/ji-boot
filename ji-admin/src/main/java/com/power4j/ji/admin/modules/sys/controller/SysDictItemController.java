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

package com.power4j.ji.admin.modules.sys.controller;

import com.power4j.ji.admin.modules.sys.dto.SysDictItemDTO;
import com.power4j.ji.admin.modules.sys.service.SysDictItemService;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.ji.common.data.crud.api.CrudApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/29
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/dictionary/items")
@Tag(name = "字典项")
public class SysDictItemController implements CrudApi<Long, SysDictItemDTO> {

	private final SysDictItemService sysDictItemService;

	@PreAuthorize("@pms.any('sys:dict:view')")
	@Override
	public ApiResponse<List<SysDictItemDTO>> readList(List<Long> idList) {
		return ApiResponseUtil.ok(sysDictItemService.readList(idList));
	}

	@PreAuthorize("@pms.any('sys:dict:view')")
	@Override
	public ApiResponse<SysDictItemDTO> read(Long id) {
		return ApiResponseUtil.ok(sysDictItemService.read(id).orElse(null));
	}

	@PreAuthorize("@pms.any('sys:dict:edit')")
	@Override
	public ApiResponse<SysDictItemDTO> post(SysDictItemDTO obj) {
		return ApiResponseUtil.ok(sysDictItemService.post(obj));
	}

	@PreAuthorize("@pms.any('sys:dict:edit')")
	@Override
	public ApiResponse<SysDictItemDTO> put(SysDictItemDTO obj) {
		return ApiResponseUtil.ok(sysDictItemService.put(obj));
	}

	@PreAuthorize("@pms.any('sys:dict:edit')")
	@Override
	public ApiResponse<SysDictItemDTO> delete(@PathVariable("id") Long id) {
		return ApiResponseUtil.ok(sysDictItemService.delete(id).orElse(null));
	}

	@GetMapping("/counter/values/{value}")
	@Operation(summary = "查找字典项", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> countOfValue(@PathVariable("value") String value,
			@Parameter(description = "排除的字典项ID") @RequestParam(required = false) Long excludeId,
			@Parameter(description = "字典ID") @RequestParam Long dictId) {
		return ApiResponseUtil.ok(sysDictItemService.countDictItemValue(value, excludeId, dictId));
	}

}
