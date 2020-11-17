package com.power4j.flygon.common.core.translator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ErrorEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String ex;

	private String info;

}
