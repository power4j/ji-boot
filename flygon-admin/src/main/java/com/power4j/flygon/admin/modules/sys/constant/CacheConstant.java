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

package com.power4j.flygon.admin.modules.sys.constant;

import com.power4j.flygon.common.cache.constant.CommonCacheConstant;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/6
 * @since 1.0
 */
public interface CacheConstant {

	interface Name {

		// formatter:off

		String DICT_CODE_TO_DICT = "sys_dict:code" + CommonCacheConstant.TTL_SEPARATOR + "1d";

		String DICT_ID_TO_DICT_ITEMS = "sys_dict_item_list:dict_id" + CommonCacheConstant.TTL_SEPARATOR + "1d";

		String USERNAME_TO_USER = "sys_user:username" + CommonCacheConstant.TTL_SEPARATOR + "8h";

		String API_TOKEN_TO_USER_TOKEN = "user_token:api_token" + CommonCacheConstant.TTL_SEPARATOR + "1d";

		String RESOURCE_TREE = "sys_resource_tree" + CommonCacheConstant.TTL_SEPARATOR + "2h";

		String USERNAME_TO_ROLES = "sys_role_list:username" + CommonCacheConstant.TTL_SEPARATOR + "1h";

		String ROLE_CODES_TO_RESOURCES = "sys_resource_list:role_code_list" + CommonCacheConstant.TTL_SEPARATOR + "1h";

		String ROLE_CODES_TO_RESOURCE_TREE = "sys_resource_tree:role_code_list" + CommonCacheConstant.TTL_SEPARATOR
				+ "1h";

		// formatter:on

	}

	interface Keys {

		//

	}

}
