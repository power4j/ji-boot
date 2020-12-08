package com.power4j.flygon.admin.modules.sys.controller;

import com.power4j.flygon.admin.modules.sys.dto.SysDictDTO;
import com.power4j.flygon.admin.modules.sys.dto.SysDictItemDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysDict;
import com.power4j.flygon.admin.modules.sys.service.SysDictItemService;
import com.power4j.flygon.admin.modules.sys.service.SysDictService;
import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.exception.BizException;
import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.model.PageData;
import com.power4j.flygon.common.core.model.PageRequest;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import com.power4j.flygon.common.data.crud.api.CrudApi;
import com.power4j.flygon.common.data.crud.constant.OpFlagEnum;
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

import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/dict")
@Tag(name = "字典管理", description = "字典管理")
public class SysDictController extends CrudController<SysDictDTO, SysDict> implements CrudApi<SysDictDTO,ApiResponse<SysDictDTO>> {
	private final SysDictService sysDictService;
	private final SysDictItemService sysDictItemService;


	@GetMapping("/page")
	@Operation(summary = "分页")
	public ApiResponse<PageData<SysDictDTO>> page(PageRequest page) {
		return ApiResponseUtil.ok(sysDictService.selectPage(page, null));
	}

	@GetMapping("/count/code/{code}")
	@Operation(summary = "查找字典代码", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> findCode(@PathVariable("code") @NotEmpty String code,
										@Parameter(description = "排除的用户ID") @RequestParam(required = false) Long excludeId) {
		return ApiResponseUtil.ok(sysDictService.countDictCode(code, excludeId));
	}

	@GetMapping("/code/{code}")
	@Operation(summary = "字典项列表")
	public ApiResponse<List<SysDictItemDTO>> listItems(@PathVariable("code") @NotEmpty String code) {
		SysDictDTO dictDTO = sysDictService.getDict(code).orElse(null);
		if(dictDTO == null){
			return ApiResponseUtil.ok(Collections.emptyList());
		}
		return ApiResponseUtil.ok(sysDictItemService.getDictItems(dictDTO.getId()));
	}
	
	@Override
	protected void prePostCheck(SysDictDTO obj) throws BizException {
		if (sysDictService.countDictCode(obj.getCode(), null) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT,String.format("字典代码不能使用: %s", obj.getCode()));
		}
	}

	@Override
	protected void prePutCheck(SysDictDTO obj,SysDict entity) throws BizException {
		if (entity == null) {
			throw new BizException(SysErrorCodes.E_CONFLICT,String.format("字典不存在"));
		}
		checkOpFlagNq(entity,OpFlagEnum.SYS_LOCKED.getValue(),"系统数据不允许修改");
		if (sysDictService.countDictCode(obj.getCode(), obj.getId()) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT,String.format("字典代码不能使用: %s", obj.getCode()));
		}
	}

	@Override
	protected void preDeleteCheck(SysDictDTO obj,SysDict entity) throws BizException {
		checkOpFlagNq(entity,OpFlagEnum.SYS_LOCKED.getValue(),"系统数据不允许删除");
	}

	@Override
	protected void preReadCheck(SysDictDTO obj) throws BizException {
		// nothing to check
	}
}
