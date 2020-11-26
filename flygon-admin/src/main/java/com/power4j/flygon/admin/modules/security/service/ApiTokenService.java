package com.power4j.flygon.admin.modules.security.service;

import com.power4j.flygon.admin.modules.security.entity.ApiTokenEntity;
import com.power4j.flygon.common.data.crud.service.CrudService;
import com.power4j.flygon.common.security.model.ApiToken;
import com.power4j.flygon.common.security.service.TokenService;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/26
 * @since 1.0
 */
public interface ApiTokenService extends CrudService<ApiToken, ApiTokenEntity> , TokenService {
}
