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

package com.power4j.ji.admin.modules.sys.service.impl;

import com.power4j.ji.admin.modules.sys.dao.SysLogMapper;
import com.power4j.ji.admin.modules.sys.dto.SysLogDTO;
import com.power4j.ji.admin.modules.sys.entity.SysLog;
import com.power4j.ji.admin.modules.sys.service.SysLogService;
import com.power4j.ji.common.data.crud.service.impl.AbstractCrudService;
import org.springframework.stereotype.Service;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/6/25
 * @since 1.0
 */
@Service
public class SysLogServiceImpl extends AbstractCrudService<SysLogMapper, SysLogDTO, SysLog> implements SysLogService {

}
