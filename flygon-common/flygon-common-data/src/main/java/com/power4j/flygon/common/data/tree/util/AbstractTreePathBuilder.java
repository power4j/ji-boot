package com.power4j.flygon.common.data.tree.util;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power4j.flygon.common.data.tree.entity.TreePath;
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
 * TreePath帮助类
 * <p/>
 * 由于目前MP不支持泛型Lambda,因此查询条件需由子类实现
 *
 * @see <a href="https://github.com/baomidou/mybatis-plus/issues/2086">mybatis-plus
 * #2086</a>
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/30
 * @since 1.0
 */
public abstract class AbstractTreePathBuilder<T extends TreePath, M extends BaseMapper<T>> {

	@Autowired
	private M dao;

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
	 * 查询条件
	 * @param id
	 * @return
	 */
	protected abstract Wrapper<T> getRemoveNodeWrapper(Serializable id);

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
	 * 祖先查询条件
	 * @param id
	 * @param distanceMin 最小距离,从0开始, -1 表示无限制
	 * @param distanceMax 最大距离,从0开始, -1 表示无限制
	 * @return
	 */
	protected abstract Wrapper<T> getLoadAncestorsWrapper(Serializable id, int distanceMin, int distanceMax);

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
	 * @param id
	 * @param distanceMin 最小距离,从0开始, -1 表示无限制
	 * @param distanceMax 最大距离,从0开始, -1 表示无限制
	 * @return
	 */
	protected abstract Wrapper<T> getLoadDescendantsWrapper(Serializable id, int distanceMin, int distanceMax);


	/**
	 * 后代查询条件
	 * @param ids
	 * @param distanceMin 最小距离,从0开始, -1 表示无限制
	 * @param distanceMax 最大距离,从0开始, -1 表示无限制
	 * @return
	 */
	protected abstract Wrapper<T> getLoadDescendantsWrapper(Collection<Serializable> ids, int distanceMin, int distanceMax);

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
	 * 距离查询条件
	 * @param distance 距离
	 * @return
	 */
	protected abstract Wrapper<T> getLoadByDistanceWrapper(int distance);

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
			Comparator comparator = Comparator.comparing(TreePath::getDistance);
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

}
