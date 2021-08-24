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

package com.power4j.ji.admin.modules.ureport.service;

import com.power4j.ji.admin.modules.ureport.dto.UrFileDTO;
import com.power4j.ji.admin.modules.ureport.entity.UrData;
import com.power4j.ji.admin.modules.ureport.vo.SearchUrFileVO;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.data.crud.service.CrudService;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/2/12
 * @since 1.0
 */
public interface UrFileService extends CrudService<UrFileDTO, UrData> {

	/**
	 * 统计name使用次数
	 * @param file
	 * @param ignoreId 排除的ID
	 * @return
	 */
	long countFileName(String file, @Nullable Long ignoreId);

	/**
	 * 查询
	 * @param file
	 * @return
	 */
	Optional<UrData> findByName(String file);

	/**
	 * 删除
	 * @param file
	 * @return
	 */
	Optional<UrFileDTO> deleteByFileName(String file);

	/**
	 * 分页查询
	 * @param pageRequest
	 * @param param
	 * @return
	 */
	PageData<UrFileDTO> selectPage(PageRequest pageRequest, @Nullable SearchUrFileVO param);

}
