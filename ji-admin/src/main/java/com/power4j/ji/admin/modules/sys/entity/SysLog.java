/*
 * Copyright 2020 ChenJun (power4j@outlook.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.power4j.ji.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.power4j.ji.common.core.model.Unique;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/6/25
 * @since 1.0
 */
@Data
@TableName("t_sys_log")
@EqualsAndHashCode(callSuper = false)
public class SysLog implements Unique, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主健
	 */
	private Long id;

	/**
	 * 请求ID
	 */
	private String requestId;

	/**
	 * 模块
	 */
	private String module;

	/**
	 * 名称
	 */
	private String apiTag;

	/**
	 * 访问时间
	 */
	private LocalDateTime accessAt;

	/**
	 * 耗时,毫秒
	 */
	private Integer duration;

	/**
	 * HTTP方法
	 */
	private String method;

	/**
	 * URL 路径
	 */
	private String path;

	/**
	 * URL 参数
	 */
	private String query;

	/**
	 * 用户ID
	 */
	private Long uid;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 访问来源
	 */
	private String location;

	/**
	 * API响应码
	 */
	private String responseCode;

	/**
	 * 异常
	 */
	private String ex;

	/**
	 * 异常信息
	 */
	private String exMsg;

	@Override
	public Long getOnlyId() {
		return id;
	}

}
