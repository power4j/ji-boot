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

package com.power4j.ji.admin.modules.schedule.service;

import com.power4j.ji.admin.modules.schedule.dto.SysJobLogDTO;
import com.power4j.ji.admin.modules.schedule.entity.SysJobLog;
import com.power4j.ji.admin.modules.schedule.vo.SearchSysJobLogVO;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/25
 * @since 1.0
 */
public interface SysJobLogService {

	/**
	 * 插入记录
	 * @param jobLog
	 * @return
	 */
	SysJobLog insertJobLog(SysJobLog jobLog);

	/**
	 * 批量删除
	 * @param idList
	 */
	void deleteBatch(List<Long> idList);

	/**
	 * 分页查询
	 * @param pageRequest
	 * @param param
	 * @return
	 */
	PageData<SysJobLogDTO> selectPage(PageRequest pageRequest, @Nullable SearchSysJobLogVO param);

}
