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

package com.power4j.ji.common.data.region;

import cn.hutool.core.text.CharSequenceUtil;
import com.power4j.coca.kit.common.number.Num;
import com.power4j.coca.kit.common.text.StringPool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/8/23
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoIp {

	public final static String UNKNOWN_ID = Integer.toString(-1);

	public final static String UNKNOWN_VALUE = "Unknown";

	private String id;

	@Nullable
	private String country;

	@Nullable
	private String zone;

	@Nullable
	private String province;

	@Nullable
	private String city;

	@Nullable
	private String isp;

	public static final GeoIp UNKNOWN = new GeoIp(UNKNOWN_ID, UNKNOWN_VALUE, UNKNOWN_VALUE, UNKNOWN_VALUE,
			UNKNOWN_VALUE, UNKNOWN_VALUE);

	public String displayString() {
		List<String> stringList = Stream.of(country, zone, province, city, isp).filter(Objects::nonNull)
				.collect(Collectors.toList());
		return CharSequenceUtil.join(StringPool.PIPE, stringList);
	}

	public String dataString() {
		List<String> stringList = Stream.of(country, zone, province, city, isp).collect(Collectors.toList());
		return CharSequenceUtil.join(StringPool.PIPE, stringList);
	}

}
