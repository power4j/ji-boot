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

package com.power4j.flygon.common.data.scope.annotitons;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/13
 * @since 1.0
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface ScopedApi {

	/**
	 * Name
	 * @return
	 */
	String name() default "";

	/**
	 * Group
	 * @return
	 */
	String group() default "";

	/**
	 * Spring EL to generate a {@code RowFilter} object
	 * @return
	 */
	String expr() default "T(io.renren.common.data.filter.support.RowFilter.ALL)";

}
