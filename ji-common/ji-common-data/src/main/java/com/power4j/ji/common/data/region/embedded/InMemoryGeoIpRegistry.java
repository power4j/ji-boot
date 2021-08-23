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

package com.power4j.ji.common.data.region.embedded;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import com.power4j.coca.kit.common.text.StringPool;
import com.power4j.ji.common.data.region.GeoIp;
import com.power4j.ji.common.data.region.GeoIpRegistry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.IndexBlock;
import org.lionsoul.ip2region.Util;
import org.springframework.lang.Nullable;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/8/23
 * @since 1.0
 */
@RequiredArgsConstructor
public class InMemoryGeoIpRegistry implements GeoIpRegistry {

	private final static int BLOCK_SIZE = IndexBlock.getIndexBlockLength();

	private final State state;

	public static InMemoryGeoIpRegistry load(InputStream inputStream) {
		State state = State.load(inputStream, BLOCK_SIZE);
		return new InMemoryGeoIpRegistry(state);
	}

	@Override
	public Optional<GeoIp> search(long ip) {
		return Optional.ofNullable(doSearch(ip)).map(this::toLocation);
	}

	@Override
	public Optional<GeoIp> search(String ip) {
		return search(Ipv4Util.ipv4ToLong(ip));
	}

	@Nullable
	protected DataBlock doSearch(long ip) {
		// search the index blocks to define the data
		int l = 0, h = state.getTotalBlocks();
		long sip, eip, pos = 0;
		while (l <= h) {
			int m = (l + h) >> 1;
			int p = (int) (state.getFirstIndex() + m * state.getBlockSize());

			sip = Util.getIntLong(state.getRawData(), p);
			if (ip < sip) {
				h = m - 1;
			}
			else {
				eip = Util.getIntLong(state.getRawData(), p + 4);
				if (ip > eip) {
					l = m + 1;
				}
				else {
					pos = Util.getIntLong(state.getRawData(), p + 8);
					break;
				}
			}
		}

		// not matched
		if (pos == 0) {
			return null;
		}

		// get the data
		int dataLen = (int) ((pos >> 24) & 0xFF);
		int dataPtr = (int) ((pos & 0x00FFFFFF));
		int cityId = (int) Util.getIntLong(state.getRawData(), dataPtr);
		String region = new String(state.getRawData(), dataPtr + 4, dataLen - 4, StandardCharsets.UTF_8);

		return new DataBlock(cityId, region, dataPtr);
	}

	@Getter
	static class State {

		private final long firstIndex;

		private final int totalBlocks;

		private final int blockSize;

		private final byte[] rawData;

		State(long firstIndex, int totalBlocks, int blockSize, byte[] rawData) {
			this.firstIndex = firstIndex;
			this.totalBlocks = totalBlocks;
			this.blockSize = blockSize;
			this.rawData = rawData;
		}

		static State load(InputStream inputStream, int blockSize) {
			byte[] data = IoUtil.readBytes(inputStream);
			long firstIndex = Util.getIntLong(data, 0);
			long lastIndex = Util.getIntLong(data, 4);
			int totalBlocks = (int) ((lastIndex - firstIndex) / blockSize) + 1;
			return new State(firstIndex, totalBlocks, blockSize, data);
		}

	}

	protected GeoIp toLocation(DataBlock src) {
		// 格式 _城市Id|国家|区域|省份|城市|ISP_
		GeoIp geoIp = new GeoIp();
		geoIp.setId(Optional.ofNullable(src.getCityId()).map(o -> Integer.toString(o)).orElse(GeoIp.UNKNOWN_VALUE));
		if (CharSequenceUtil.isNotEmpty(src.getRegion())) {
			String[] parts = CharSequenceUtil.splitToArray(src.getRegion(), StringPool.PIPE);
			geoIp.setCountry(ArrayUtil.get(parts, 0));
			geoIp.setZone(ArrayUtil.get(parts, 1));
			geoIp.setProvince(ArrayUtil.get(parts, 2));
			geoIp.setCity(ArrayUtil.get(parts, 3));
			geoIp.setIsp(ArrayUtil.get(parts, 4));
		}
		return geoIp;
	}

}
