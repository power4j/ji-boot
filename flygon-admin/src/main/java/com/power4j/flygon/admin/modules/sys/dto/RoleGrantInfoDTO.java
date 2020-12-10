package com.power4j.flygon.admin.modules.sys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.power4j.flygon.common.core.validate.Groups;
import com.power4j.flygon.common.data.crud.dto.BaseDTO;
import com.power4j.flygon.common.data.crud.util.Unique;
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
public class RoleGrantInfoDTO extends BaseDTO implements Unique, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@Schema(description = "用户ID")
	@NotNull(groups = { Groups.Default.class })
	private Long userId;

	/**
	 * 授权类型 0 普通 1 可二次授权
	 */
	@Schema(description = "授权类型 0 普通 1 可二次授权", example = "0")
	@NotNull(groups = { Groups.Default.class })
	@Pattern(regexp = "0|1", message = "状态只能是 0 或者 1", groups = { Groups.Default.class })
	private String grantType;


	/**
	 * 角色信息
	 */
	@Schema(description = "角色信息", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private SysRoleDTO roleInfo;
}
