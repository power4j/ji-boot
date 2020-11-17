package com.power4j.flygon.common.data.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.power4j.flygon.common.core.constant.CrudConstant;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 自动填充字段
 * @see <a href = https://baomidou.com/guide/auto-fill-metainfo.html>auto-fill-metainfo</a>
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/22
 * @since 1.0
 */
public class AutoFillHandler implements MetaObjectHandler {
	@Override
	public void insertFill(MetaObject metaObject) {
		this.strictInsertFill(metaObject, CrudConstant.COLUMN_CREATE_AT, LocalDateTime.class, LocalDateTime.now());
		this.strictInsertFill(metaObject, CrudConstant.COLUMN_LOGICAL_DEL, Integer.class, CrudConstant.LOGICAL_NOT_DEL);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.strictInsertFill(metaObject, CrudConstant.COLUMN_UPDATE_AT, LocalDateTime.class, LocalDateTime.now());
	}
}
