package com.power4j.flygon.common.security.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/24
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ApiToken implements Serializable {
	private static final long serialVersionUID = 1L;
	@Schema(description = "访问令牌")
	private String token;
	@Schema(description = "过期时间(yyyy-MM-dd'T'HH:mm:ss'Z')", example = "2020-01-01T20:05:01Z")
	private LocalDateTime expireIn;
	@Schema(description = "发布者")
	private String issuedBy;
}
