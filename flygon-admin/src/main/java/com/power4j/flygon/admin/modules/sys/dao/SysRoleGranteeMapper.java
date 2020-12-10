package com.power4j.flygon.admin.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power4j.flygon.admin.modules.sys.entity.SysRoleGrant;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/14
 * @since 1.0
 */
@Mapper
public interface SysRoleGranteeMapper extends BaseMapper<SysRoleGrant> {

}
