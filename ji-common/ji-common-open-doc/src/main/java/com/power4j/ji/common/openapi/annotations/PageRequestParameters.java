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

package com.power4j.ji.common.openapi.annotations;

import com.power4j.ji.common.core.constant.CrudConstant;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/4/27
 * @since 1.0
 */
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Parameters({
		@Parameter(in = ParameterIn.QUERY, description = "页码", name = CrudConstant.QRY_PAGE_INDEX,
				content = @Content(schema = @Schema(type = "integer", defaultValue = "1"))),
		@Parameter(in = ParameterIn.QUERY, description = "每一页的记录条数", name = CrudConstant.QRY_PAGE_SIZE,
				content = @Content(schema = @Schema(type = "integer", defaultValue = "20"))),
		@Parameter(in = ParameterIn.QUERY, name = CrudConstant.QRY_PAGE_ORDER_PROP, description = "排序字段,比如 createTime",
				content = @Content(array = @ArraySchema(schema = @Schema(type = "string")))),
		@Parameter(in = ParameterIn.QUERY, name = CrudConstant.QRY_PAGE_ORDER_ASC, description = "是否升序",
				content = @Content(schema = @Schema(type = "boolean", defaultValue = "true"))) })
public @interface PageRequestParameters {

}
