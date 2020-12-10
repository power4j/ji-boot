package com.power4j.flygon.admin.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power4j.flygon.admin.modules.sys.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/10
 * @since 1.0
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

	/**
	 * 查询授予用户的角色
	 * @param userId
	 * @param grantType
	 * @return
	 */
	List<SysRole> selectByUserId(@Param("userId") Long userId,@Param("grantType") String grantType);
}
