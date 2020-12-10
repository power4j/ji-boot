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

package com.power4j.flygon.common.core.resolver;

import com.power4j.flygon.common.core.constant.CrudConstant;
import com.power4j.flygon.common.core.model.PageRequest;
import com.power4j.flygon.common.core.util.NumUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Resolver for {@code PageRequest}
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-17
 * @since 1.0
 */
public class PageRequestResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterType().equals(PageRequest.class);
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
			NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
		HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

		String orderProp = request.getParameter(CrudConstant.QRY_PAGE_ORDER_PROP);
		String asc = request.getParameter(CrudConstant.QRY_PAGE_ORDER_ASC);
		String page = request.getParameter(CrudConstant.QRY_PAGE_INDEX);
		String size = request.getParameter(CrudConstant.QRY_PAGE_SIZE);

		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(NumUtil.parseInt(page, 1));
		pageRequest.setSize(NumUtil.parseInt(size, 20));

		if(orderProp != null && !orderProp.isEmpty()){
			if(Boolean.FALSE.toString().equalsIgnoreCase(asc)){
				pageRequest.setDesc(Arrays.asList(orderProp));
			} else {
				pageRequest.setAsc(Arrays.asList(orderProp));
			}
		}

		return pageRequest;
	}

}
