package com.power4j.flygon.admin.modules.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import com.power4j.flygon.admin.modules.sys.dto.SysRoleGrantDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysRoleGrant;
import com.power4j.flygon.admin.modules.sys.entity.SysUser;
import com.power4j.flygon.admin.modules.sys.service.SysRoleGrantService;
import com.power4j.flygon.admin.modules.sys.service.SysRoleService;
import com.power4j.flygon.admin.modules.sys.service.SysUserService;
import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.exception.BizException;
import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import com.power4j.flygon.common.data.crud.api.CrudApi;
import com.power4j.flygon.common.data.crud.controller.CrudController;
import com.power4j.flygon.common.security.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/20
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/role/grantee")
@Tag(name = "角色授权")
public class SysRoleGrantController extends CrudController<SysRoleGrantDTO, SysRoleGrant>
		implements CrudApi<SysRoleGrantDTO> {
	private final SysUserService sysUserService;
	private final SysRoleService sysRoleService;
	private final SysRoleGrantService sysRoleGrantService;

	@GetMapping("/users/{uid}")
	@Operation(summary = "用户的授权列表")
	public ApiResponse<List<SysRoleGrantDTO>> getList(@Parameter(description = "用户ID") @PathVariable Long uid,
													  @Parameter(description = "授权类型") @RequestParam(required = false) String grantType) {
		List<SysRoleGrantDTO> list = sysRoleGrantService.getByUser(uid,grantType).stream().map(o -> BeanUtil.toBean(o,SysRoleGrantDTO.class) )
				.collect(Collectors.toList());
		return ApiResponseUtil.ok(list);
	}
}
