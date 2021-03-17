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

package com.power4j.ji.common.data.crud.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.power4j.ji.common.core.model.Unique;
import com.power4j.ji.common.data.crud.util.SysCtl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

/**
 * 通用实体
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-19
 * @since 1.0
 */
@Getter
@Setter
public abstract class BaseEntity implements Unique, SysCtl {

	/**
	 * 主健
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 数据标记 0 普通数据, 1 系统保护数据
	 */
	private Integer sysFlag;

	/**
	 * 逻辑删除标志
	 */
	@Nullable
	@TableLogic
	private LocalDateTime delFlag;

	/**
	 * 创建时间
	 */
	@Nullable
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createAt;

	/**
	 * 更新时间
	 */
	@Nullable
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateAt;

	@Override
	public Long getOnlyId() {
		return id;
	}

	@Override
	public Integer getCtlFlag() {
		return sysFlag;
	}

}
