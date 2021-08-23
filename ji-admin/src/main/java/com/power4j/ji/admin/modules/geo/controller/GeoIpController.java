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

package com.power4j.ji.admin.modules.geo.controller;

import cn.hutool.core.bean.BeanUtil;
import com.power4j.ji.admin.modules.geo.dto.GeoIpDTO;
import com.power4j.ji.admin.modules.sys.dto.SysResourceDTO;
import com.power4j.ji.admin.modules.sys.dto.SysRoleDTO;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.ji.common.data.region.GeoIp;
import com.power4j.ji.common.data.region.GeoIpRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/8/23
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ip/geo")
@Tag(name = "IP地理信息")
public class GeoIpController {

	private final GeoIpRegistry geoIpRegistry;

	@GetMapping("/ipv4/{ip}")
	@Operation(summary = "查询IP地理信息")
	public ApiResponse<GeoIpDTO> searchGeoIp(@PathVariable("ip") String ip) {
		GeoIp geoIp = geoIpRegistry.search(ip).orElse(GeoIp.UNKNOWN);
		return ApiResponseUtil.ok(BeanUtil.toBean(geoIp, GeoIpDTO.class));
	}

}
