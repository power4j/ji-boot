package com.power4j.flygon.admin.modules.sys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.power4j.flygon.common.core.validate.Groups;
import com.power4j.flygon.common.data.crud.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/9
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysParamDTO extends BaseDTO implements Serializable {

	/**
	 * 参数键,唯一
	 */
	@Schema(description = "参数名,唯一", example = "project-home")
	@NotNull(groups = { Groups.Default.class })
	@Size(min = 1, max = 255, groups = { Groups.Default.class })
	private String paramKey;

	/**
	 * 参数值
	 */
	@Schema(description = "值", example = "power4j.com")
	@NotNull(groups = { Groups.Default.class })
	@Size(min = 1, max = 2000, groups = { Groups.Default.class })
	private String paramValue;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	@Size(min = 1, max = 40, groups = { Groups.Default.class })
	private String remarks;

	/**
	 * 状态 0 有效 1 停用
	 */
	@Schema(description = "状态 0 有效 1 停用", example = "0")
	@NotNull(groups = { Groups.Default.class })
	@Pattern(regexp = "0|1", message = "状态只能是 0 或者 1", groups = { Groups.Default.class })
	private String status;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String createBy;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String updateBy;

}
