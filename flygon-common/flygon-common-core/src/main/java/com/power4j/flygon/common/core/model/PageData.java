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

package com.power4j.flygon.common.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.power4j.flygon.common.core.constant.CrudConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Paged data
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-17
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Schema(title = "分页数据")
public class PageData<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(title = "页码")
	@JsonProperty(CrudConstant.QRY_PAGE_INDEX)
	private Integer page;

	@Schema(title = "每页条数")
	@JsonProperty(CrudConstant.QRY_PAGE_SIZE)
	private Integer size;

	@Schema(title = "总条数")
	@JsonProperty(CrudConstant.QRY_PAGE_TOTAL)
	private Integer total;

	@Schema(title = "数据")
	@JsonProperty(CrudConstant.QRY_PAGE_RECORDS)
	private List<T> records = new ArrayList<>();

	public <U> PageData<U> map(Function<T, U> converter) {
		PageData<U> pageData = new PageData<>();
		pageData.setPage(page);
		pageData.setSize(size);
		pageData.setTotal(total);
		pageData.setRecords(records.stream().map(o -> converter.apply(o)).collect(Collectors.toList()));
		return pageData;
	}

}
