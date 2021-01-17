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

package com.power4j.ji.admin.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power4j.ji.admin.modules.sys.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/10
 * @since 1.0
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

	/**
	 * 查询授予用户的角色
	 * @param userId
	 * @param grantType
	 * @return
	 */
	List<SysRole> selectByUserId(@Param("userId") Long userId, @Param("grantType") String grantType);

}
