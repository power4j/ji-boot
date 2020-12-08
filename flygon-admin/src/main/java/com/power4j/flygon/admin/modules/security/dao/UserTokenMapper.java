package com.power4j.flygon.admin.modules.security.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power4j.flygon.admin.modules.security.entity.UserToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/26
 * @since 1.0
 */
@Mapper
public interface UserTokenMapper extends BaseMapper<UserToken> {

	/**
	 * 行锁
	 * @param uid
	 * @return
	 */
	UserToken selectForUpdate(@Param("uid") Long uid);

}
