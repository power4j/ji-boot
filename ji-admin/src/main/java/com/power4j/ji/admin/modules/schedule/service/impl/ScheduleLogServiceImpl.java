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

package com.power4j.ji.admin.modules.schedule.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.power4j.ji.admin.modules.schedule.dao.ScheduleLogMapper;
import com.power4j.ji.admin.modules.schedule.dto.ScheduleLogDTO;
import com.power4j.ji.admin.modules.schedule.entity.ScheduleLog;
import com.power4j.ji.admin.modules.schedule.service.ScheduleLogService;
import com.power4j.ji.admin.modules.schedule.vo.SearchScheduleLogVO;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.data.crud.util.CrudUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/25
 * @since 1.0
 */
@Service
public class ScheduleLogServiceImpl extends ServiceImpl<ScheduleLogMapper, ScheduleLog> implements ScheduleLogService {

	@Override
	public ScheduleLog insertJobLog(ScheduleLog jobLog) {
		save(jobLog);
		return jobLog;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteBatch(List<Long> idList) {
		if (idList != null && !idList.isEmpty()) {
			removeByIds(idList);
		}
	}

	@Override
	public PageData<ScheduleLogDTO> selectPage(PageRequest pageRequest, @Nullable SearchScheduleLogVO param) {
		Wrapper<ScheduleLog> wrapper = new QueryWrapper<>();
		if (param != null) {
			LocalDate start = ArrayUtil.get(param.getStartTimeIn(), 0);
			LocalDate end = ArrayUtil.get(param.getStartTimeIn(), 1);
			wrapper = new QueryWrapper<ScheduleLog>().lambda()
					.eq(CharSequenceUtil.isNotBlank(param.getTaskBean()), ScheduleLog::getTaskBean, param.getTaskBean())
					.eq(param.getSuccess() != null, ScheduleLog::getSuccess, param.getSuccess())
					.likeRight(CharSequenceUtil.isNotEmpty(param.getEx()), ScheduleLog::getEx, param.getEx())
					.ge(null != start, ScheduleLog::getStartTime, CrudUtil.dayStart(start))
					.le(null != end, ScheduleLog::getStartTime, CrudUtil.dayEnd(end));
		}
		Page<ScheduleLog> page = getBaseMapper().selectPage(
				CrudUtil.toPage(pageRequest, Collections.singletonList(new OrderItem("id", false))), wrapper);
		return CrudUtil.toPageData(page).map(o -> BeanUtil.toBean(o, ScheduleLogDTO.class));
	}

}
