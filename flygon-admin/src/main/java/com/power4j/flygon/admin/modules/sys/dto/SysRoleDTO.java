package com.power4j.flygon.admin.modules.sys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.power4j.flygon.common.core.validate.Groups;
import com.power4j.flygon.common.data.crud.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/10
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SysRoleDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编码
	 */
	@Schema(description = "编码", example = "admin")
	@Size(min = 6, max = 20, groups = { Groups.Default.class })
	private String code;

	/**
	 * 名称
	 */
	@Schema(description = "名称", example = "site admin")
	@Size(min = 6, max = 20, groups = { Groups.Default.class })
	private String name;

	/**
	 * 状态 0 正常 1 禁用
	 */
	@Schema(description = "状态 0 有效 1 停用", example = "0")
	@NotNull(groups = { Groups.Default.class })
	@Pattern(regexp = "0|1", message = "状态只能是 0 或者 1", groups = { Groups.Default.class })
	private String status;

	/**
	 * 拥有者
	 */
	@Schema(description = "拥有者", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long owner;

}
