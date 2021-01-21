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

package com.power4j.ji.admin.modules.sys.service;

import com.power4j.ji.admin.modules.sys.dto.SysRoleDTO;
import com.power4j.ji.admin.modules.sys.entity.SysRole;
import com.power4j.ji.common.data.crud.service.CrudService;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/10
 * @since 1.0
 */
public interface SysRoleService extends CrudService<SysRoleDTO, SysRole> {

	/**
	 * 统计编码使用次数
	 * @param code
	 * @param ignoreId 排除的ID
	 * @return
	 */
	int countRoleCode(String code, @Nullable Long ignoreId);

	/**
	 * 角色列表
	 * @param username
	 * @param grantType
	 * @return
	 */
	List<SysRole> listForUser(String username, @Nullable String grantType);

	/**
	 * 读取
	 * @param code
	 * @return
	 */
	Optional<SysRoleDTO> readByCode(String code);

}
