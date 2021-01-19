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

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power4j.ji.admin.modules.schedule.dao.SysJobMapper;
import com.power4j.ji.admin.modules.schedule.dto.SysJobDTO;
import com.power4j.ji.admin.modules.schedule.entity.SysJob;
import com.power4j.ji.admin.modules.schedule.service.SysJobService;
import com.power4j.ji.admin.modules.schedule.vo.SearchSysJobVO;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.exception.BizException;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.core.util.SpringContextUtil;
import com.power4j.ji.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.ji.common.data.crud.util.CrudUtil;
import com.power4j.ji.common.schedule.job.ExecutionPlan;
import com.power4j.ji.common.schedule.job.MisFirePolicyEnum;
import com.power4j.ji.common.schedule.job.PlanStatusEnum;
import com.power4j.ji.common.schedule.job.Task;
import com.power4j.ji.common.schedule.quartz.util.CronUtil;
import com.power4j.ji.common.schedule.quartz.util.QuartzUtil;
import com.power4j.ji.common.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/20
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SysJobServiceImpl extends AbstractCrudService<SysJobMapper, SysJobDTO, SysJob> implements SysJobService {

	private final Scheduler scheduler;

	@Override
	protected SysJobDTO prePostHandle(SysJobDTO dto) {
		validateTaskBean(dto.getTaskBean());
		validateCron(dto.getCron());
		dto.setCreateBy(SecurityUtil.getLoginUsername().orElse(null));
		return super.prePostHandle(dto);
	}

	@Override
	public SysJobDTO post(SysJobDTO dto) {
		SysJobDTO ret = super.post(dto);
		QuartzUtil.createPlan(scheduler, toExecutionPlan(ret));
		return ret;
	}

	@Override
	protected SysJobDTO prePutHandle(SysJobDTO dto) {
		validateTaskBean(dto.getTaskBean());
		validateCron(dto.getCron());
		dto.setUpdateBy(SecurityUtil.getLoginUsername().orElse(null));
		return super.prePutHandle(dto);
	}

	@Override
	public SysJobDTO put(SysJobDTO dto) {
		SysJobDTO ret = super.put(dto);
		QuartzUtil.updatePlan(scheduler, toExecutionPlan(ret));
		return ret;
	}

	@Override
	public PageData<SysJobDTO> selectPage(PageRequest pageRequest, @Nullable SearchSysJobVO param) {
		Wrapper<SysJob> wrapper = Wrappers.emptyWrapper();
		if (param != null) {
			wrapper = new QueryWrapper<SysJob>().lambda()
					.eq(StrUtil.isNotBlank(param.getStatus()), SysJob::getStatus, param.getStatus())
					.likeRight(StrUtil.isNotEmpty(param.getGroupName()), SysJob::getGroupName, param.getGroupName());
		}
		Page<SysJob> page = getBaseMapper()
				.selectPage(CrudUtil.toPage(pageRequest, Arrays.asList(new OrderItem("create_at", true))), wrapper);
		return CrudUtil.toPageData(page).map(o -> toDto(o));
	}

	protected ExecutionPlan toExecutionPlan(SysJobDTO sysJob) {
		ExecutionPlan executionPlan = new ExecutionPlan();
		executionPlan.setPlanId(sysJob.getId());
		executionPlan.setGroupName(sysJob.getGroupName());
		executionPlan.setCron(sysJob.getCron());
		executionPlan.setTaskBean(sysJob.getTaskBean());
		executionPlan.setParam(sysJob.getParam());
		executionPlan.setDescription(sysJob.getRemarks());
		executionPlan.setStatus(PlanStatusEnum.parse(sysJob.getStatus()));
		executionPlan.setMisFirePolicy(MisFirePolicyEnum.parse(sysJob.getMisFirePolicy()));
		executionPlan.setFailRecover(sysJob.getFailRecover());

		return executionPlan;

	}

	protected void validateTaskBean(String beanName) {

		Object bean = SpringContextUtil.getBean(beanName);
		if (bean == null) {
			throw new BizException(SysErrorCodes.E_PARAM_INVALID, String.format("Bean不存在:{}", beanName));
		}
		if (!(bean instanceof Task)) {
			throw new BizException(SysErrorCodes.E_PARAM_INVALID,
					String.format("{}不是{}的子类", beanName, Task.class.getSimpleName()));
		}
	}

	protected void validateCron(String cron) {
		CronUtil.parse(cron).orElseThrow(
				() -> new BizException(SysErrorCodes.E_PARAM_INVALID, String.format("不是有效的Cron表达式:{}", cron)));
	}

}
