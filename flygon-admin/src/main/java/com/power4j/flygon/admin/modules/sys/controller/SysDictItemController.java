package com.power4j.flygon.admin.modules.sys.controller;

import com.power4j.flygon.admin.modules.sys.dto.SysDictDTO;
import com.power4j.flygon.admin.modules.sys.dto.SysDictItemDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysDictItem;
import com.power4j.flygon.admin.modules.sys.service.SysDictItemService;
import com.power4j.flygon.admin.modules.sys.service.SysDictService;
import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.exception.BizException;
import com.power4j.flygon.common.core.model.ApiResponse;
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
import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/29
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/dict-items")
@Tag(name = "字典项管理", description = "字典项管理")
public class SysDictItemController extends CrudController<SysDictItemDTO, SysDictItem> implements CrudApi<SysDictItemDTO, ApiResponse<SysDictItemDTO>> {
	private final SysDictService sysDictService;
	private final SysDictItemService sysDictItemService;


	@GetMapping("/count/values/{value}")
	@Operation(summary = "查找字典项", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> findCode(@PathVariable("value") @NotEmpty String value,
										 @Parameter(description = "排除的字典项ID") @RequestParam(required = false) Long excludeId,
										 @Parameter(description = "字典ID") @RequestParam Long dictId) {
		return ApiResponseUtil.ok(sysDictItemService.countDictItemValue(value,excludeId,dictId));
	}

	@Override
	protected void prePostCheck(SysDictItemDTO obj) throws BizException {
		SysDictDTO dictDTO = getDict(obj.getDictId());
		if(dictDTO == null){
			throw new BizException(SysErrorCodes.E_BAD_REQUEST,String.format("字典不存在: %s", obj.getDictId()));
		}
		if(sysDictItemService.countDictItemValue(obj.getValue(),null,dictDTO.getId()) > 0){
			throw new BizException(SysErrorCodes.E_BAD_REQUEST,String.format("已经存在相同的字典项: %s:%s", dictDTO.getCode(), obj.getValue()));
		}
	}

	@Override
	protected void prePutCheck(SysDictItemDTO obj, SysDictItem entity) throws BizException {
		if (entity == null) {
			throw new BizException(SysErrorCodes.E_CONFLICT,String.format("字典项不存在,id: %d",obj.getId()));
		}
		checkOpFlagNq(entity,OpFlagEnum.SYS_LOCKED.getValue(),"系统数据不允许修改");
		SysDictDTO dictDTO = getDict(obj.getDictId());
		if(sysDictItemService.countDictItemValue(obj.getValue(),obj.getId(),dictDTO.getId()) > 0){
			throw new BizException(SysErrorCodes.E_BAD_REQUEST,String.format("已经存在相同的字典项: %s:%s", dictDTO.getCode(), obj.getValue()));
		}
	}

	@Override
	protected void preReadCheck(SysDictItemDTO obj) throws BizException {

	}

	@Override
	protected void preDeleteCheck(SysDictItemDTO obj, SysDictItem entity) throws BizException {
		checkOpFlagNq(entity,OpFlagEnum.SYS_LOCKED.getValue(),"系统数据不允许删除");
	}

	protected SysDictDTO getDict(Serializable dictId){
		SysDictDTO dictDTO = sysDictService.read(dictId).orElse(null);
		if(dictDTO == null){
			throw new BizException(SysErrorCodes.E_BAD_REQUEST,String.format("字典不存在,id: %d", dictId));
		}
		return dictDTO;
	}
}
