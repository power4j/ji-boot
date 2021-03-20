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

package com.power4j.ji.common.data.crud.util;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
@UtilityClass
public class CrudUtil {

	/**
	 * 转为数据库字段名称
	 * @param prop
	 * @return
	 */
	public String toColumnName(String prop) {
		return StringUtils.camelToUnderline(prop);
	}

	/**
	 * 转换 PageRequest
	 * @param pageRequest
	 * @param <T>
	 * @return
	 */
	public <T> Page<T> toPage(PageRequest pageRequest) {
		Page<T> page = new Page<>();
		page.setSize(Optional.ofNullable(pageRequest.getSize()).orElse(0));
		page.setCurrent(Optional.ofNullable(pageRequest.getPage()).orElse(0));

		if (CollUtil.isNotEmpty(pageRequest.getAsc())) {
			List<OrderItem> orderItems = pageRequest.getAsc().stream()
					.map(col -> new OrderItem(toColumnName(col), true)).collect(Collectors.toList());
			page.addOrder(orderItems);
		}
		if (CollUtil.isNotEmpty(pageRequest.getDesc())) {
			List<OrderItem> orderItems = pageRequest.getDesc().stream()
					.map(col -> new OrderItem(toColumnName(col), false)).collect(Collectors.toList());
			page.addOrder(orderItems);
		}
		return page;
	}

	/**
	 * 转换 PageRequest
	 * @param pageRequest
	 * @param <T>
	 * @return
	 */
	public <T> Page<T> toPage(PageRequest pageRequest, List<OrderItem> defaultOrders) {
		Page<T> page = toPage(pageRequest);
		if (CollUtil.isEmpty(page.getRecords()) && CollUtil.isNotEmpty(defaultOrders)) {
			page.addOrder(defaultOrders);
		}
		return page;
	}

	/**
	 * 转换 Page
	 * @param page
	 * @param <T>
	 * @return
	 */
	public <T> PageData<T> toPageData(Page<T> page) {
		PageData<T> pageData = new PageData<>();
		pageData.setPage((int) page.getCurrent());
		pageData.setSize((int) page.getSize());
		pageData.setTotal((int) page.getTotal());
		pageData.setRecords(page.getRecords());
		return pageData;
	}

	/**
	 * @param localDate
	 * @return
	 */
	public LocalDateTime dayStart(@Nullable LocalDate localDate) {
		return localDate == null ? null : localDate.atTime(LocalTime.MIN);
	}

	/**
	 * @param localDate
	 * @return
	 */
	public LocalDateTime dayEnd(@Nullable LocalDate localDate) {
		return localDate == null ? null : localDate.atTime(LocalTime.MAX);
	}

}
