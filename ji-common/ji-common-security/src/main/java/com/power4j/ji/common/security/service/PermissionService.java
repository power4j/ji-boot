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

package com.power4j.ji.common.security.service;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/30
 * @since 1.0
 */
@Slf4j
public class PermissionService {

	@Value("${ji-boot.security.api.permit-all:false}")
	private Boolean permitAll;

	/**
	 * 拥有其中一项权限
	 * @param permissions
	 * @return
	 */
	public boolean any(String... permissions) {
		if(permitAll){
			log.warn("警告:接口鉴权已经停用");
			return true;
		}
		return CollectionUtil.containsAny(getPermissions(), Arrays.asList(permissions));
	}

	/**
	 * 拥有所有权限
	 * @param permissions
	 * @return
	 */
	public boolean all(String... permissions) {
		if(permitAll){
			log.warn("警告:接口鉴权已经停用");
			return true;
		}
		return getPermissions().containsAll(Arrays.asList(permissions));
	}

	private Set<String> getPermissions() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return Collections.emptySet();
		}
		return authentication.getAuthorities().stream().map(o -> o.getAuthority()).collect(Collectors.toSet());
	}

}
