package com.power4j.flygon.admin.modules.security.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power4j.flygon.admin.modules.security.entity.ApiTokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/26
 * @since 1.0
 */
@Mapper
public interface ApiTokenMapper extends BaseMapper<ApiTokenEntity> {

	/**
	 * 行锁
	 * @param username
	 * @return
	 */
	ApiTokenEntity selectForUpdate(@Param("username") String username);
}
