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

package com.power4j.ji.admin.modules.sys.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.power4j.coca.kit.common.async.TaskKit;
import com.power4j.ji.admin.modules.sys.dao.SysLogMapper;
import com.power4j.ji.admin.modules.sys.entity.SysLog;
import com.power4j.ji.common.data.region.GeoIp;
import com.power4j.ji.common.data.region.GeoIpRegistry;
import com.power4j.ji.common.security.audit.AccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/6/25
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AccessEventListener implements ApplicationListener<PayloadApplicationEvent<AccessEvent>> {

	private final SysLogMapper sysLogMapper;

	private final GeoIpRegistry geoIpRegistry;

	@Override
	public void onApplicationEvent(PayloadApplicationEvent<AccessEvent> event) {
		TaskKit.runLater(() -> this.save(event.getPayload()), ((duration, throwable) -> {
			log.error("访问日志保存失败", throwable);
		}));
	}

	private void save(AccessEvent accessEvent) {
		final SysLog entity = BeanUtil.toBean(accessEvent, SysLog.class);
		String geo = geoIpRegistry.search(accessEvent.getLocation()).orElse(GeoIp.UNKNOWN).displayString();
		entity.setGeoIp(CharSequenceUtil.subPre(geo, 40));
		sysLogMapper.insert(entity);
	}

}
