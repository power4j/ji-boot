package com.power4j.flygon.admin.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power4j.flygon.admin.modules.sys.entity.SysResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/30
 * @since 1.0
 */
@Mapper
public interface SysResourceMapper extends BaseMapper<SysResource> {

	/**
	 * 查询授予角色的资源
	 * @param roleCodes
	 * @return
	 */
	List<SysResource> selectByRoles(@Param("roleCodes") Collection<String> roleCodes);
}
