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
import com.power4j.ji.admin.modules.sys.dto.SysParamDTO;
import com.power4j.ji.admin.modules.sys.service.SysParamService;
import com.power4j.ji.admin.modules.sys.vo.SearchSysParamVO;
import com.power4j.ji.common.core.constant.CrudConstant;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.ji.common.data.crud.api.CrudApi;
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

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/9
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/param")
@Tag(name = "参数")
public class SysParamController implements CrudApi<Long, SysParamDTO> {

	private final SysParamService sysParamService;

	@PreAuthorize("@pms.any('sys:param:view')")
	@Override
	public ApiResponse<List<SysParamDTO>> readList(List<Long> idList) {
		return ApiResponseUtil.ok(sysParamService.readList(idList));
	}

	@PreAuthorize("@pms.any('sys:param:view')")
	@Override
	public ApiResponse<SysParamDTO> read(Long id) {
		return ApiResponseUtil.ok(sysParamService.read(id).orElse(null));
	}

	@PreAuthorize("@pms.any('sys:param:add')")
	@Override
	public ApiResponse<SysParamDTO> post(SysParamDTO obj) {
		return ApiResponseUtil.ok(sysParamService.post(obj));
	}

	@PreAuthorize("@pms.any('sys:param:edit')")
	@Override
	public ApiResponse<SysParamDTO> put(SysParamDTO obj) {
		return ApiResponseUtil.ok(sysParamService.put(obj));
	}

	@PreAuthorize("@pms.any('sys:param:del')")
	@Override
	public ApiResponse<SysParamDTO> delete(Long id) {
		return ApiResponseUtil.ok(sysParamService.delete(id).orElse(null));
	}

	@PreAuthorize("@pms.any('sys:param:view')")
	@GetMapping("/page")
	@Operation(summary = "分页",
			parameters = {
					@Parameter(name = CrudConstant.QRY_PAGE_INDEX, in = ParameterIn.QUERY, description = "页码,从1开始"),
					@Parameter(name = CrudConstant.QRY_PAGE_SIZE, in = ParameterIn.QUERY, description = "记录数量"),
					@Parameter(name = CrudConstant.QRY_PAGE_ORDER_PROP, in = ParameterIn.QUERY, description = "排序字段"),
					@Parameter(name = CrudConstant.QRY_PAGE_ORDER_ASC, in = ParameterIn.QUERY, description = "是否升序"),
					@Parameter(name = "paramKey", in = ParameterIn.QUERY, description = "参数名,支持模糊查询"),
					@Parameter(name = "status", in = ParameterIn.QUERY, description = "状态") })
	public ApiResponse<PageData<SysParamDTO>> page(@Parameter(hidden = true) PageRequest page,
			@Parameter(hidden = true) SearchSysParamVO param) {
		return ApiResponseUtil
				.ok(sysParamService.selectPage(page, param == null ? null : BeanUtil.toBean(param, SysParamDTO.class)));
	}

	@GetMapping("/key/{key}")
	@Operation(summary = "根据参数名查找")
	public ApiResponse<SysParamDTO> getByKey(@PathVariable("key") String key) {
		return ApiResponseUtil.ok(sysParamService.findByKey(key).orElse(null));
	}

	@GetMapping("/counter/key/{key}")
	@Operation(summary = "统计参数名", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> countOfKey(@PathVariable("key") @NotEmpty String key,
			@Parameter(description = "排除的ID") @RequestParam(required = false) Long excludeId) {
		return ApiResponseUtil.ok(sysParamService.countParamKey(key, excludeId));
	}

}
