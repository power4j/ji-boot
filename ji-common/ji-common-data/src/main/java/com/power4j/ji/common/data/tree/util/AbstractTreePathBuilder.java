/*
 * Copyright 2020 ChenJun (power4j@outlook.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.power4j.ji.common.data.tree.util;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power4j.ji.common.data.tree.entity.TreePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * TreePath数据维护
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/30
 * @since 1.0
 */
public abstract class AbstractTreePathBuilder<T extends TreePath, M extends BaseMapper<T>> {

	@Autowired
	private M dao;

	/**
	 * 创建 {@code Wrapper} 对象
	 * @return
	 */
	protected abstract QueryWrapper<T> createWrapper();

	/**
	 * 创建对象
	 * @param ancestor
	 * @param descendant
	 * @param distance
	 * @return
	 */
	protected abstract T createNode(Long ancestor, Long descendant, Integer distance);

	/**
	 * 新增节点路径
	 * @param parentId
	 * @param id
	 */
	public void insertNode(Long parentId, Long id) {
		insertNodes(createPath(parentId, id));
	}

	/**
	 * 删除节点路径
	 * @param id
	 */
	public void removeNode(Serializable id) {
		dao.delete(getRemoveNodeWrapper(id));
	}

	/**
	 * 加载所有节点路径
	 * @return
	 */
	public List<T> loadAll() {
		return dao.selectList(null);
	}

	/**
	 * 加载某个节点的祖先
	 * @param id
	 * @param distanceMin 最小距离,从0开始, -1 表示无限制
	 * @param distanceMax 最大距离,从0开始, -1 表示无限制
	 * @return
	 */
	public List<T> loadAncestors(Serializable id, int distanceMin, int distanceMax) {
		return dao.selectList(getLoadAncestorsWrapper(id, distanceMin, distanceMax));
	}

	/**
	 * 后代查询条件
	 * @param ids
	 * @param distanceMin 最小距离,从0开始, -1 表示无限制
	 * @param distanceMax 最大距离,从0开始, -1 表示无限制
	 * @return
	 */
	protected Wrapper<T> getLoadDescendantsWrapper(Collection<Serializable> ids, int distanceMin, int distanceMax) {
		QueryWrapper<T> wrapper = createWrapper();
		wrapper.in("ancestor", ids).ge(distanceMin >= 0, "distance", distanceMin).le(distanceMax >= 0, "distance",
				distanceMax);
		return wrapper;
	}

	/**
	 * 加载某个节点的后代
	 * @param id
	 * @param distanceMin 最小距离,从0开始, -1 表示无限制
	 * @param distanceMax 最大距离,从0开始, -1 表示无限制
	 * @return
	 */
	public List<T> loadDescendants(Serializable id, int distanceMin, int distanceMax) {
		return dao.selectList(getLoadDescendantsWrapper(id, distanceMin, distanceMax));
	}

	/**
	 * 加载节点的后代
	 * @param ids
	 * @param distanceMin 最小距离,从0开始, -1 表示无限制
	 * @param distanceMax 最大距离,从0开始, -1 表示无限制
	 * @return
	 */
	public List<T> loadDescendants(Collection<Serializable> ids, int distanceMin, int distanceMax) {
		return dao.selectList(getLoadDescendantsWrapper(ids, distanceMin, distanceMax));
	}

	/**
	 * 加载距离关系
	 * @param distance 距离
	 * @return
	 */
	public List<T> loadByDistance(int distance) {
		return dao.selectList(getLoadByDistanceWrapper(distance));
	}

	/**
	 * 创建新节点的所有路径
	 * @param parentId
	 * @param id
	 * @return
	 */
	protected List<T> createPath(Long parentId, Long id) {
		List<T> list = sortByDistance(loadAncestors(parentId, -1, -1), true);
		list = list.stream().map(o -> createNode(o.getAncestor(), id, o.getDistance() + 1))
				.collect(Collectors.toList());
		list.add(createNode(id, id, 0));

		return list;
	}

	/**
	 * 建议重写这个方法,提高性能
	 * @param nodePaths
	 */
	protected void insertNodes(Collection<T> nodePaths) {
		nodePaths.forEach(o -> dao.insert(o));
	}

	public List<T> sortByDistance(@Nullable List<T> list, boolean asc) {
		if (list != null && !list.isEmpty()) {
			Comparator<T> comparator = Comparator.comparing(TreePath::getDistance);
			list.sort(asc ? comparator : comparator.reversed());
		}
		return list;
	}

	/**
	 * 过滤层级
	 * @param list
	 * @param min 最小值,从0开始
	 * @param max 最大值,从0开始, -1 表示无限制
	 * @return
	 */
	public List<T> filterByDistance(@Nullable List<T> list, int min, int max) {
		List<T> filtered = new ArrayList<>();
		if (list != null && !list.isEmpty()) {
			Predicate<T> predicate = o -> o.getDistance() >= min;
			predicate = max < 0 ? predicate : predicate.and(o -> o.getDistance() <= max);
			filtered = list.stream().filter(predicate).collect(Collectors.toList());
		}
		return filtered;
	}

	// ------------------------------------------------------------------------------------
	// ~ Wrappers

	/**
	 * 距离查询条件
	 * @param distance 距离
	 * @return
	 */
	protected Wrapper<T> getLoadByDistanceWrapper(int distance) {
		QueryWrapper<T> wrapper = createWrapper();
		wrapper.eq("distance", distance);
		return wrapper;
	}

	/**
	 * 祖先查询条件
	 * @param id
	 * @param distanceMin 最小距离,从0开始, -1 表示无限制
	 * @param distanceMax 最大距离,从0开始, -1 表示无限制
	 * @return
	 */
	protected Wrapper<T> getLoadAncestorsWrapper(Serializable id, int distanceMin, int distanceMax) {
		QueryWrapper<T> wrapper = createWrapper();
		wrapper.eq("descendant", id).ge(distanceMin >= 0, "distance", distanceMin).le(distanceMax >= 0, "distance",
				distanceMax);
		return wrapper;
	}

	/**
	 * 后代查询条件
	 * @param id
	 * @param distanceMin 最小距离,从0开始, -1 表示无限制
	 * @param distanceMax 最大距离,从0开始, -1 表示无限制
	 * @return
	 */
	protected Wrapper<T> getLoadDescendantsWrapper(Serializable id, int distanceMin, int distanceMax) {
		QueryWrapper<T> wrapper = createWrapper();
		wrapper.eq("ancestor", id).ge(distanceMin >= 0, "distance", distanceMin).le(distanceMax >= 0, "distance",
				distanceMax);
		return wrapper;
	}

	/**
	 * 查询条件
	 * @param id
	 * @return
	 */
	protected Wrapper<T> getRemoveNodeWrapper(Serializable id) {
		QueryWrapper<T> wrapper = createWrapper();
		wrapper.eq("descendant", id);
		return wrapper;
	}

}
