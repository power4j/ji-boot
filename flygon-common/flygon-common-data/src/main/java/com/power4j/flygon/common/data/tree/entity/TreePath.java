package com.power4j.flygon.common.data.tree.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/30
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TreePath implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 祖先ID
	 */
	private Long ancestor;

	/**
	 * 后代ID
	 */
	private Long descendant;

	/**
	 * 层距离,指向自己时距离为0
	 */
	private Integer distance;

}