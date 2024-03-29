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

import com.power4j.ji.admin.modules.sys.dto.SysPositionDTO;
import com.power4j.ji.admin.modules.sys.entity.SysPosition;
import com.power4j.ji.common.data.crud.service.CrudService;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/4/9
 * @since 1.0
 */
public interface SysPositionService extends CrudService<SysPositionDTO, SysPosition> {

	/**
	 * 统计编码使用次数
	 * @param code
	 * @param ignoreId 排除的ID
	 * @return
	 */
	long countRoleCode(String code, @Nullable Long ignoreId);

	/**
	 * 角色列表
	 * @param username
	 * @param grantType
	 * @return
	 */
	List<SysPositionDTO> listForUser(String username, @Nullable String grantType);

	/**
	 * 读取
	 * @param code
	 * @return
	 */
	Optional<SysPositionDTO> readByCode(String code);

}
