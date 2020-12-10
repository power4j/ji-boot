package com.power4j.flygon.admin.modules.sys.service;

import com.power4j.flygon.admin.modules.sys.dto.SysParamDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysParam;
import com.power4j.flygon.common.data.crud.service.CrudService;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/9
 * @since 1.0
 */
public interface SysParamService extends CrudService<SysParamDTO, SysParam> {

	/**
	 * 统计key使用次数
	 * @param key
	 * @param ignoreId 排除的ID
	 * @return
	 */
	int countParamKey(String key, @Nullable Long ignoreId);

	/**
	 * 根据Key查找
	 * @param key
	 * @return
	 */
	Optional<SysParamDTO> findByKey(String key);

}
