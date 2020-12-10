package com.power4j.flygon.admin.modules.sys.controller;

import com.power4j.flygon.admin.modules.sys.dto.SysDictItemDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysDictItem;
import com.power4j.flygon.admin.modules.sys.service.SysDictItemService;
import com.power4j.flygon.admin.modules.sys.service.SysDictService;
import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import com.power4j.flygon.common.data.crud.api.CrudApi;
import com.power4j.flygon.common.data.crud.controller.CrudController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/29
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/dictionary/items")
@Tag(name = "字典项")
public class SysDictItemController extends CrudController<SysDictItemDTO, SysDictItem>
		implements CrudApi<SysDictItemDTO> {

	private final SysDictService sysDictService;

	private final SysDictItemService sysDictItemService;

	@GetMapping("/counter/values/{value}")
	@Operation(summary = "查找字典项", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> countOfValue(@PathVariable("value") String value,
			@Parameter(description = "排除的字典项ID") @RequestParam(required = false) Long excludeId,
			@Parameter(description = "字典ID") @RequestParam Long dictId) {
		return ApiResponseUtil.ok(sysDictItemService.countDictItemValue(value, excludeId, dictId));
	}

}
