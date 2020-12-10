package com.power4j.flygon.admin.modules.security.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.power4j.flygon.common.data.crud.util.Unique;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/26
 * @since 1.0
 */
@Data
@TableName("t_api_token")
public class UserToken implements Unique, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主健
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 访问令牌
	 */
	private String token;

	/**
	 * 用户UID
	 */
	private Long uuid;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 过期时间
	 */
	private LocalDateTime expireIn;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createAt;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateAt;

	@Override
	public Serializable getOnlyId() {
		return id;
	}

}
