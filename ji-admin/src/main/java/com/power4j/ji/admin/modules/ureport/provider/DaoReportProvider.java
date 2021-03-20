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

package com.power4j.ji.admin.modules.ureport.provider;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bstek.ureport.exception.ReportException;
import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;
import com.power4j.ji.admin.modules.ureport.constant.ReportConstants;
import com.power4j.ji.admin.modules.ureport.dao.UrFileMapper;
import com.power4j.ji.admin.modules.ureport.entity.UrData;
import com.power4j.ji.admin.modules.ureport.util.UrUtil;
import com.power4j.ji.common.data.crud.constant.LowAttrEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/2/12
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class DaoReportProvider implements ReportProvider {

	private final UrFileMapper urFileMapper;

	protected LambdaQueryWrapper<UrData> queryFileWrapper(String file) {
		LambdaQueryWrapper<UrData> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(UrData::getFile, file);
		return queryWrapper;
	}

	protected Optional<UrData> findOne(String file) {
		return Optional.ofNullable(urFileMapper.selectOne(queryFileWrapper(file)));
	}

	@Override
	public InputStream loadReport(String file) {
		UrData urData = findOne(file).orElseThrow(() -> new ReportException(String.format("%s 不存在", file)));
		return new ByteArrayInputStream(urData.getData());
	}

	@Override
	public void deleteReport(String file) {
		UrData urData = findOne(file).orElse(null);
		if (Objects.nonNull(urData)) {
			if (LowAttrEnum.SYS_LOCKED.getValue() == urData.getSysFlag()) {
				throw new ReportException(String.format("不允许删除: %s", file));
			}
			urFileMapper.deleteById(urData.getId());
		}
	}

	@Override
	public List<ReportFile> getReportFiles() {
		return urFileMapper.selectList(null).stream().map(UrUtil::toReportFile).collect(Collectors.toList());
	}

	@Override
	public void saveReport(String file, String content) {
		UrData urData = findOne(file).orElse(null);
		if (urData == null) {
			urData = new UrData();
			urData.setFile(file);
			urData.setData(content.getBytes(StandardCharsets.UTF_8));
			urData.setCreateAt(LocalDateTime.now());
			urFileMapper.insert(urData);
		}
		else {
			if (LowAttrEnum.SYS_LOCKED.getValue() == urData.getSysFlag()) {
				throw new ReportException(String.format("不允许修改: %s", file));
			}
			urData.setData(content.getBytes(StandardCharsets.UTF_8));
			urData.setUpdateAt(LocalDateTime.now());
			urFileMapper.updateById(urData);
		}
	}

	@Override
	public String getName() {
		return "DB Store";
	}

	@Override
	public boolean disabled() {
		return false;
	}

	@Override
	public String getPrefix() {
		return ReportConstants.PROVIDER_PREFIX;
	}

}
