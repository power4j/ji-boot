package com.power4j.flygon.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.power4j.flygon.admin.modules.sys.dao.SysDictItemMapper;
import com.power4j.flygon.admin.modules.sys.dto.SysDictItemDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysDictItem;
import com.power4j.flygon.admin.modules.sys.service.SysDictItemService;
import com.power4j.flygon.common.data.crud.service.impl.AbstractCrudService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
@Service
public class SysDictItemServiceImpl extends AbstractCrudService<SysDictItemMapper, SysDictItemDTO, SysDictItem> implements SysDictItemService {
	@Override
	public List<SysDictItemDTO> getDictItems(Serializable dictId) {
		Wrapper<SysDictItem> wrapper = Wrappers.<SysDictItem>lambdaQuery()
				.eq(SysDictItem::getDictId,dictId);
		return toDtoList(getBaseMapper().selectList(wrapper));
	}

	@Override
	public int countDictItemValue(String value, Long ignoreId,Serializable dictId) {
		Wrapper<SysDictItem> wrapper = Wrappers.<SysDictItem>lambdaQuery()
				.eq(SysDictItem::getDictId,dictId)
				.eq(SysDictItem::getValue, value)
				.ne(null != ignoreId, SysDictItem::getId, ignoreId);
		return getBaseMapper().selectCount(wrapper);
	}
}
