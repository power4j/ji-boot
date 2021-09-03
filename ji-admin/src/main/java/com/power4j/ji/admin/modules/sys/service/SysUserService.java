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

import com.power4j.ji.admin.modules.sys.dto.SysUserDTO;
import com.power4j.ji.admin.modules.sys.entity.SysUser;
import com.power4j.ji.admin.modules.sys.vo.SearchSysUserVO;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.data.crud.service.CrudService;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-19
 * @since 1.0
 */
public interface SysUserService extends CrudService<SysUserDTO, SysUser> {

	/**
	 * 根据用户名查询
	 * @param username
	 * @return
	 */
	Optional<SysUser> getByUsername(String username);

	/**
	 * 分页查询
	 * @param pageRequest
	 * @param param
	 * @return
	 */
	PageData<SysUserDTO> selectPage(PageRequest pageRequest, @Nullable SearchSysUserVO param);

	/**
	 * 统计用户名使用次数
	 * @param username
	 * @param ignoreId 排除的ID
	 * @return
	 */
	long countUsername(String username, @Nullable Long ignoreId);

	/**
	 * 根据用户ID查询
	 * @param uid
	 * @return
	 */
	Optional<SysUser> getByUserId(long uid);

}
