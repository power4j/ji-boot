package com.power4j.flygon.admin.modules.sys.service;

import com.power4j.flygon.admin.modules.sys.dto.SysDictDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysDict;
import com.power4j.flygon.common.data.crud.service.CrudService;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
public interface SysDictService extends CrudService<SysDictDTO, SysDict> {
	/**
	 * 统计字典代码
	 * @param code
	 * @param ignoreId
	 * @return
	 */
	int countDictCode(String code, @Nullable Long ignoreId);

	/**
	 * 查询
	 * @param code
	 * @return
	 */
	Optional<SysDictDTO> getDict(String code);
}
