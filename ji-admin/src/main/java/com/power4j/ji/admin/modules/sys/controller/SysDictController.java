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

import cn.hutool.core.bean.BeanUtil;
import com.power4j.ji.admin.modules.sys.dto.SysDictDTO;
import com.power4j.ji.admin.modules.sys.dto.SysDictItemDTO;
import com.power4j.ji.admin.modules.sys.service.SysDictItemService;
import com.power4j.ji.admin.modules.sys.service.SysDictService;
import com.power4j.ji.admin.modules.sys.vo.SearchSysDictVO;
import com.power4j.ji.common.core.constant.CrudConstant;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.ji.common.data.crud.api.CrudApi;
import com.power4j.ji.common.openapi.annotations.PageRequestParameters;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Dictionaries
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/dictionaries")
@Tag(name = "字典")
public class SysDictController implements CrudApi<Long, SysDictDTO> {

	private final SysDictService sysDictService;

	private final SysDictItemService sysDictItemService;

	@PreAuthorize("@pms.any('sys:dict:view')")
	@Override
	public ApiResponse<List<SysDictDTO>> readList(List<Long> idList) {
		return ApiResponseUtil.ok(sysDictService.readList(idList));
	}

	@PreAuthorize("@pms.any('sys:dict:view')")
	@Override
	public ApiResponse<SysDictDTO> read(Long id) {
		return ApiResponseUtil.ok(sysDictService.read(id).orElse(null));
	}

	@PreAuthorize("@pms.any('sys:dict:add')")
	@Override
	public ApiResponse<SysDictDTO> post(SysDictDTO obj) {
		return ApiResponseUtil.ok(sysDictService.post(obj));
	}

	@PreAuthorize("@pms.any('sys:dict:edit')")
	@Override
	public ApiResponse<SysDictDTO> put(SysDictDTO obj) {
		return ApiResponseUtil.ok(sysDictService.put(obj));
	}

	@PreAuthorize("@pms.any('sys:dict:del')")
	@Override
	public ApiResponse<SysDictDTO> delete(Long id) {
		return ApiResponseUtil.ok(sysDictService.delete(id).orElse(null));
	}

	@PreAuthorize("@pms.any('sys:dict:view')")
	@GetMapping("/page")
	@PageRequestParameters
	@Operation(summary = "分页",
			parameters = { @Parameter(name = "name", in = ParameterIn.QUERY, description = "字典名称,支持模糊查询") })
	public ApiResponse<PageData<SysDictDTO>> page(@Parameter(hidden = true) PageRequest page,
			@Parameter(hidden = true) SearchSysDictVO param) {
		return ApiResponseUtil.ok(sysDictService.selectPage(page, BeanUtil.toBean(param, SysDictDTO.class)));
	}

	@GetMapping("/counter/code")
	@Operation(summary = "查找字典代码", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> countOfCode(@RequestParam String value,
			@Parameter(description = "排除的ID") @RequestParam(required = false) Long excludeId) {
		return ApiResponseUtil.ok(sysDictService.countDictCode(value, excludeId));
	}

	@GetMapping("/code/{code}")
	@Operation(summary = "字典项列表")
	public ApiResponse<List<SysDictItemDTO>> listItems(@PathVariable("code") String code) {
		SysDictDTO dictDTO = sysDictService.getDict(code).orElse(null);
		if (dictDTO == null) {
			return ApiResponseUtil.ok(Collections.emptyList());
		}
		return ApiResponseUtil.ok(sysDictItemService.getDictItems(dictDTO.getId()));
	}

}
