package com.power4j.flygon.admin.modules.sys.dto;

import com.power4j.flygon.common.core.validate.Groups;
import com.power4j.flygon.common.data.crud.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/14
 * @since 1.0
 */
@Data
@Schema(title = "角色授权")
public class SysRoleGrantDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@Schema(description = "用户ID")
	@NotNull(groups = { Groups.Default.class })
	private Long userId;

	/**
	 * 授权类型 0 普通 1 再授权
	 */
	@Schema(description = "授权类型 0 普通 1 再授权", example = "0")
	@NotNull(groups = { Groups.Default.class })
	@Pattern(regexp = "0|1", message = "授权类型只能是 0 或者 1", groups = { Groups.Default.class })
	private String grantType;

	/**
	 * 角色ID
	 */
	@Schema(description = "角色ID")
	@NotNull(groups = { Groups.Default.class })
	private Long roleId;
}
