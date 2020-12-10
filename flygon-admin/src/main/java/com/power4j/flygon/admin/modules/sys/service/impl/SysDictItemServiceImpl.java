package com.power4j.flygon.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.power4j.flygon.admin.modules.sys.dao.SysDictItemMapper;
import com.power4j.flygon.admin.modules.sys.dao.SysDictMapper;
import com.power4j.flygon.admin.modules.sys.dto.SysDictItemDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysDict;
import com.power4j.flygon.admin.modules.sys.entity.SysDictItem;
import com.power4j.flygon.admin.modules.sys.service.SysDictItemService;
import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.exception.BizException;
import com.power4j.flygon.common.data.crud.service.impl.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SysDictItemServiceImpl extends AbstractCrudService<SysDictItemMapper, SysDictItemDTO, SysDictItem>
		implements SysDictItemService {

	private final SysDictMapper dictMapper;

	@Override
	public List<SysDictItemDTO> getDictItems(Serializable dictId) {
		Wrapper<SysDictItem> wrapper = Wrappers.<SysDictItem>lambdaQuery().eq(SysDictItem::getDictId, dictId);
		return toDtoList(getBaseMapper().selectList(wrapper));
	}

	@Override
	public int countDictItemValue(String value, Long ignoreId, Serializable dictId) {
		Wrapper<SysDictItem> wrapper = Wrappers.<SysDictItem>lambdaQuery().eq(SysDictItem::getDictId, dictId)
				.eq(SysDictItem::getValue, value).ne(null != ignoreId, SysDictItem::getId, ignoreId);
		return getBaseMapper().selectCount(wrapper);
	}

	@Override
	protected SysDictItemDTO prePostHandle(SysDictItemDTO dto) {
		SysDict dict = dictMapper.selectById(dto.getDictId());
		if (dict == null) {
			throw new BizException(SysErrorCodes.E_BAD_REQUEST, String.format("字典不存在: %s", dto.getDictId()));
		}
		if (countDictItemValue(dto.getValue(), null, dto.getId()) > 0) {
			throw new BizException(SysErrorCodes.E_BAD_REQUEST,
					String.format("已经存在相同的字典项: %s:%s", dict.getCode(), dto.getValue()));
		}
		return super.prePostHandle(dto);
	}

	@Override
	protected SysDictItemDTO prePutHandle(SysDictItemDTO dto) {
		SysDict dict = dictMapper.selectById(dto.getDictId());
		if (countDictItemValue(dto.getValue(), dto.getId(), dict.getId()) > 0) {
			throw new BizException(SysErrorCodes.E_BAD_REQUEST,
					String.format("已经存在相同的字典项: %s:%s", dict.getCode(), dto.getValue()));
		}
		return super.prePutHandle(dto);
	}

	@Override
	protected SysDictItem preDeleteHandle(Serializable id) {
		return super.preDeleteHandle(id);
	}
}
