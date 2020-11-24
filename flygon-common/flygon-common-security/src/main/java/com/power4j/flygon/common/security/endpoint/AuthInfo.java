package com.power4j.flygon.common.security.endpoint;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/24
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public  class AuthInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "用户名")
	private String username;
	@Schema(description = "口令")
	private String password;
}