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

package com.power4j.ji.admin.modules.ureport.util;

import com.bstek.ureport.provider.report.ReportFile;
import com.power4j.ji.admin.modules.ureport.entity.UrData;
import com.power4j.ji.common.core.util.DateTimeUtil;
import lombok.experimental.UtilityClass;

import java.util.Date;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/2/12
 * @since 1.0
 */
@UtilityClass
public class UrUtil {

	/**
	 * 对象转换
	 * @param urData
	 * @return
	 */
	public ReportFile toReportFile(UrData urData) {
		Date updateDate = DateTimeUtil.toDate(Optional.ofNullable(urData.getUpdateAt()).orElse(urData.getCreateAt()));
		return new ReportFile(urData.getFile(), updateDate);
	}

}
