package com.power4j.flygon.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.power4j.flygon.admin.modules.sys.dao.SysDictItemMapper;
import com.power4j.flygon.admin.modules.sys.dao.SysDictMapper;
import com.power4j.flygon.admin.modules.sys.dto.SysDictDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysDict;
import com.power4j.flygon.admin.modules.sys.entity.SysDictItem;
import com.power4j.flygon.admin.modules.sys.service.SysDictService;
import com.power4j.flygon.common.data.crud.service.impl.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends AbstractCrudService<SysDictMapper, SysDictDTO, SysDict> implements SysDictService {
	private final SysDictItemMapper sysDictItemMapper;
	@Override
	public int countDictCode(String code, Long ignoreId) {
		Wrapper<SysDict> wrapper = Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, code)
				.ne(null != ignoreId, SysDict::getId, ignoreId);
		return getBaseMapper().selectCount(wrapper);
	}

	@Override
	public Optional<SysDictDTO> getDict(String code) {
		Wrapper<SysDict> wrapper = Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, code);
		return searchOne(wrapper);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Optional<SysDictDTO> delete(Serializable id) {
		sysDictItemMapper.delete(Wrappers.<SysDictItem>lambdaQuery().eq(SysDictItem::getDictId,id));
		return super.delete(id);
	}
}
