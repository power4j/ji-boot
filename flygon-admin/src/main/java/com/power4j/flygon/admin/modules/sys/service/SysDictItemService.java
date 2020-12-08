package com.power4j.flygon.admin.modules.sys.service;

import com.power4j.flygon.admin.modules.sys.dto.SysDictItemDTO;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
public interface SysDictItemService {

	/**
	 * 读取字典项
	 * @param dictId
	 * @return
	 */
	List<SysDictItemDTO> getDictItems(Serializable dictId);

	/**
	 * 统计字典项
	 * @param value
	 * @param ignoreId
	 * @param dictId
	 * @return
	 */
	int countDictItemValue(String value, @Nullable Long ignoreId,Serializable dictId);
}
