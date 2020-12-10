package com.power4j.flygon.admin.modules.sys.controller;

import com.power4j.flygon.admin.modules.sys.dto.SysDictDTO;
import com.power4j.flygon.admin.modules.sys.dto.SysDictItemDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysDict;
import com.power4j.flygon.admin.modules.sys.service.SysDictItemService;
import com.power4j.flygon.admin.modules.sys.service.SysDictService;
import com.power4j.flygon.common.core.constant.CrudConstant;
import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.model.PageData;
import com.power4j.flygon.common.core.model.PageRequest;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import com.power4j.flygon.common.data.crud.api.CrudApi;
import com.power4j.flygon.common.data.crud.controller.CrudController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;

/**
 * Dictionaries
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/dictionaries")
@Tag(name = "字典")
public class SysDictController extends CrudController<SysDictDTO, SysDict>
		implements CrudApi<SysDictDTO> {

	private final SysDictService sysDictService;

	private final SysDictItemService sysDictItemService;

	@GetMapping("/page")
	@Operation(summary = "分页",
			parameters = {
					@Parameter(name = CrudConstant.QRY_PAGE_INDEX, in = ParameterIn.QUERY, description = "页码,从1开始"),
					@Parameter(name = CrudConstant.QRY_PAGE_SIZE, in = ParameterIn.QUERY, description = "记录数量"),
					@Parameter(name = CrudConstant.QRY_PAGE_ORDER_PROP, in = ParameterIn.QUERY, description = "排序字段"),
					@Parameter(name = CrudConstant.QRY_PAGE_ORDER_ASC, in = ParameterIn.QUERY, description = "是否升序") })
	public ApiResponse<PageData<SysDictDTO>> page(@Parameter(hidden = true) PageRequest page) {
		return ApiResponseUtil.ok(sysDictService.selectPage(page, null));
	}

	@GetMapping("/counter/code/{code}")
	@Operation(summary = "查找字典代码", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> countOfCode(@PathVariable("code") @NotEmpty String code,
			@Parameter(description = "排除的ID") @RequestParam(required = false) Long excludeId) {
		return ApiResponseUtil.ok(sysDictService.countDictCode(code, excludeId));
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
