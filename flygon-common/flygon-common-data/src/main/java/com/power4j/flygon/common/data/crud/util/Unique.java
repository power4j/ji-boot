package com.power4j.flygon.common.data.crud.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
public interface Unique {

	/**
	 * 分布式唯一ID
	 * @return
	 */
	@JsonIgnore
	Serializable getOnlyId();

}
