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

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power4j.ji.admin.modules.schedule.dao.ScheduleJobMapper;
import com.power4j.ji.admin.modules.schedule.dto.ScheduleJobDTO;
import com.power4j.ji.admin.modules.schedule.entity.ScheduleJob;
import com.power4j.ji.admin.modules.schedule.service.ScheduleJobService;
import com.power4j.ji.admin.modules.schedule.util.ScheduleUtil;
import com.power4j.ji.admin.modules.schedule.vo.SearchScheduleJobVO;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.exception.BizException;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.core.util.SpringContextUtil;
import com.power4j.ji.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.ji.common.data.crud.util.CrudUtil;
import com.power4j.ji.common.schedule.quartz.job.ITask;
import com.power4j.ji.common.schedule.quartz.job.PlanStatusEnum;
import com.power4j.ji.common.schedule.quartz.util.CronUtil;
import com.power4j.ji.common.schedule.quartz.util.QuartzUtil;
import com.power4j.ji.common.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/20
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleJobServiceImpl extends AbstractCrudService<ScheduleJobMapper, ScheduleJobDTO, ScheduleJob>
		implements ScheduleJobService {

	private final Scheduler scheduler;

	@Override
	protected ScheduleJobDTO prePostHandle(ScheduleJobDTO dto) {
		validateTaskBean(dto.getTaskBean());
		validateCron(dto.getCron());
		dto.setCreateBy(SecurityUtil.getLoginUsername().orElse(null));
		return super.prePostHandle(dto);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ScheduleJobDTO post(ScheduleJobDTO dto) {
		ScheduleJobDTO ret = super.post(dto);
		QuartzUtil.createPlan(scheduler, ScheduleUtil.toExecutionPlan(ret));
		return ret;
	}

	@Override
	protected ScheduleJobDTO prePutHandle(ScheduleJobDTO dto) {
		validateTaskBean(dto.getTaskBean());
		validateCron(dto.getCron());

		ScheduleJob entity = checkExists(dto.getOnlyId());
		checkEditable(entity);
		if (PlanStatusEnum.NORMAL.getValue().equals(entity.getStatus())) {
			throw new BizException(SysErrorCodes.E_CONFLICT, "请先停止调度该任务");
		}
		dto.setUpdateBy(SecurityUtil.getLoginUsername().orElse(null));
		return dto;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ScheduleJobDTO put(ScheduleJobDTO dto) {
		ScheduleJobDTO ret = super.put(dto);
		QuartzUtil.updatePlan(scheduler, ScheduleUtil.toExecutionPlan(ret));
		return ret;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Optional<ScheduleJobDTO> delete(Serializable id) {
		ScheduleJob scheduleJob = preDeleteHandle(id);
		if (scheduleJob != null) {
			removeById(id);
			QuartzUtil.deletePlan(scheduler, scheduleJob.getId(), scheduleJob.getGroupName());
			return Optional.of(toDto(scheduleJob));
		}
		return Optional.empty();
	}

	@Override
	public PageData<ScheduleJobDTO> selectPage(PageRequest pageRequest, @Nullable SearchScheduleJobVO param) {
		Wrapper<ScheduleJob> wrapper = Wrappers.emptyWrapper();
		if (param != null) {
			wrapper = new QueryWrapper<ScheduleJob>().lambda()
					.eq(CharSequenceUtil.isNotBlank(param.getStatus()), ScheduleJob::getStatus, param.getStatus())
					.likeRight(CharSequenceUtil.isNotEmpty(param.getGroupName()), ScheduleJob::getGroupName,
							param.getGroupName());
		}
		Page<ScheduleJob> page = getBaseMapper()
				.selectPage(CrudUtil.toPage(pageRequest, Arrays.asList(new OrderItem("create_at", true))), wrapper);
		return CrudUtil.toPageData(page).map(o -> toDto(o));
	}

	@Override
	public String scheduleNow(Long jobId, boolean force, String fireBy) {
		ScheduleJobDTO job = require(jobId);
		if (!PlanStatusEnum.NORMAL.getValue().equals(job.getStatus()) && !force) {
			throw new BizException(SysErrorCodes.E_CONFLICT, "任务已停止调度,请先恢复调度或者强制执行");
		}
		// FIXME: 限制频率
		return QuartzUtil.triggerNow(scheduler, ScheduleUtil.toExecutionPlan(job), force, fireBy);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void pauseJob(Long jobId) {
		ScheduleJobDTO job = require(jobId);
		if (!PlanStatusEnum.PAUSE.getValue().equals(job.getStatus())) {
			job.setStatus(PlanStatusEnum.PAUSE.getValue());
			job.setUpdateBy(SecurityUtil.getLoginUsername().orElse(null));
			updateById(toEntity(job));
			QuartzUtil.pausePlan(scheduler, job.getId(), job.getGroupName());
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Optional<LocalDateTime> resumeJob(Long jobId) {
		ScheduleJobDTO job = require(jobId);
		if (!PlanStatusEnum.NORMAL.getValue().equals(job.getStatus())) {
			job.setStatus(PlanStatusEnum.NORMAL.getValue());
			job.setUpdateBy(SecurityUtil.getLoginUsername().orElse(null));
			updateById(toEntity(job));
			return QuartzUtil.resumePlan(scheduler, job.getId(), job.getGroupName());
		}
		return QuartzUtil.getNextScheduleTime(scheduler, job.getId(), job.getGroupName());
	}

	@Override
	public List<ScheduleJobDTO> listAll() {
		return toDtoList(getBaseMapper().selectList(null));
	}

	protected ScheduleJobDTO require(Long jobId) {
		return read(jobId).orElseThrow(() -> new BizException(SysErrorCodes.E_NOT_FOUND, "任务不存在"));
	}

	protected void validateTaskBean(String beanName) {

		Object bean = SpringContextUtil.getBean(beanName).orElseThrow(
				() -> new BizException(SysErrorCodes.E_PARAM_INVALID, String.format("Bean不存在:%s", beanName)));

		if (!(bean instanceof ITask)) {
			throw new BizException(SysErrorCodes.E_PARAM_INVALID,
					String.format("{}不是{}的子类", beanName, ITask.class.getSimpleName()));
		}
	}

	protected void validateCron(String cron) {
		CronUtil.parse(cron).orElseThrow(
				() -> new BizException(SysErrorCodes.E_PARAM_INVALID, String.format("不是有效的Cron表达式:%s", cron)));
	}

}
