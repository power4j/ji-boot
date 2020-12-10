package com.power4j.flygon.admin.modules.security.service;

import com.power4j.flygon.admin.modules.security.entity.UserToken;
import com.power4j.flygon.common.data.crud.service.BaseService;
import com.power4j.flygon.common.security.service.TokenService;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/26
 * @since 1.0
 */
public interface ApiTokenService extends BaseService<UserToken>, TokenService {

}
