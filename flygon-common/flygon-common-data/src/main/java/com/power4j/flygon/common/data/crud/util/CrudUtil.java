package com.power4j.flygon.common.data.crud.util;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power4j.flygon.common.core.model.PageData;
import com.power4j.flygon.common.core.model.PageRequest;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
@UtilityClass
public class CrudUtil {

	/**
	 * 转换 PageRequest
	 * @param pageRequest
	 * @param <T>
	 * @return
	 */
	public <T> Page<T> toPage(PageRequest pageRequest) {
		Page<T> page = new Page<>();
		page.setSize(pageRequest.getSize());
		page.setCurrent(pageRequest.getPage());

		if (CollUtil.isNotEmpty(pageRequest.getAsc())) {
			List<OrderItem> orderItems = pageRequest.getAsc().stream().map(col -> new OrderItem(col, true))
					.collect(Collectors.toList());
			page.addOrder(orderItems);
		}
		if (CollUtil.isNotEmpty(pageRequest.getDesc())) {
			List<OrderItem> orderItems = pageRequest.getDesc().stream().map(col -> new OrderItem(col, false))
					.collect(Collectors.toList());
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
		pageData.setPage(page.getCurrent());
		pageData.setSize(page.getSize());
		pageData.setTotal(page.getTotal());
		pageData.setRecords(page.getRecords());
		return pageData;
	}

	/**
	 * @param localDate
	 * @return
	 */
	public LocalDateTime dayStart(LocalDate localDate) {
		return localDate == null ? null : localDate.atTime(LocalTime.MIN);
	}

	/**
	 * @param localDate
	 * @return
	 */
	public LocalDateTime dayEnd(LocalDate localDate) {
		return  localDate == null ? null : localDate.atTime(LocalTime.MAX);
	}

}
