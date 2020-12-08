package com.power4j.flygon.common.data.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.power4j.flygon.common.core.validate.Groups;
import com.power4j.flygon.common.data.crud.util.Unique;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
@Getter
@Setter
public abstract class BaseDTO implements Unique {
	/**
	 * 主健
	 */
	@Schema(description = "主健")
	@NotNull(groups = { Groups.Update.class })
	@Null(groups = { Groups.Create.class },message = "主健ID由系统分配")
	private Long id;

	/**
	 * 数据标记 0 普通数据, 1 系统保护数据
	 */
	@Schema(description = "数据标记 0 普通数据, 1 系统保护数据", defaultValue = "0")
	private Integer opFlag;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime updateAt;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime createAt;

	@Override
	public Serializable getOnlyId() {
		return id;
	}
}
