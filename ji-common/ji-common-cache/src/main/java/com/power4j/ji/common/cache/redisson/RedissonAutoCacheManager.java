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

package com.power4j.ji.common.cache.redisson;

import com.power4j.ji.common.cache.util.CacheUtil;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonCache;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/29
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class RedissonAutoCacheManager implements CacheManager, ResourceLoaderAware, InitializingBean {

	ResourceLoader resourceLoader;

	private boolean dynamic = true;

	private boolean allowNullValues = true;

	private CacheConfig globalConfig = new CacheConfig();

	Codec codec;

	RedissonClient redisson;

	Map<String, CacheConfig> configMap = new ConcurrentHashMap<String, CacheConfig>();

	ConcurrentMap<String, Cache> instanceMap = new ConcurrentHashMap<String, Cache>();

	String configLocation;

	/**
	 * Creates CacheManager supplied by Redisson instance
	 * @param redisson object
	 */
	public RedissonAutoCacheManager(RedissonClient redisson) {
		this(redisson, (String) null, null);
	}

	/**
	 * Creates CacheManager supplied by Redisson instance and Cache config mapped by Cache
	 * name
	 * @param redisson object
	 * @param config object
	 */
	public RedissonAutoCacheManager(RedissonClient redisson, Map<String, ? extends CacheConfig> config) {
		this(redisson, config, null);
	}

	/**
	 * Creates CacheManager supplied by Redisson instance, Codec instance and Cache config
	 * mapped by Cache name.
	 * <p>
	 * Each Cache instance share one Codec instance.
	 * @param redisson object
	 * @param config object
	 * @param codec object
	 */
	public RedissonAutoCacheManager(RedissonClient redisson, Map<String, ? extends CacheConfig> config, Codec codec) {
		this.redisson = redisson;
		this.configMap = (Map<String, CacheConfig>) config;
		this.codec = codec;
	}

	/**
	 * Creates CacheManager supplied by Redisson instance and Cache config mapped by Cache
	 * name.
	 * <p>
	 * Loads the config file from the class path, interpreting plain paths as class path
	 * resource names that include the package path (e.g. "mypackage/myresource.txt").
	 * @param redisson object
	 * @param configLocation path
	 */
	public RedissonAutoCacheManager(RedissonClient redisson, String configLocation) {
		this(redisson, configLocation, null);
	}

	/**
	 * Creates CacheManager supplied by Redisson instance, Codec instance and Config
	 * location path.
	 * <p>
	 * Each Cache instance share one Codec instance.
	 * <p>
	 * Loads the config file from the class path, interpreting plain paths as class path
	 * resource names that include the package path (e.g. "mypackage/myresource.txt").
	 * @param redisson object
	 * @param configLocation path
	 * @param codec object
	 */
	public RedissonAutoCacheManager(RedissonClient redisson, String configLocation, Codec codec) {
		this.redisson = redisson;
		this.configLocation = configLocation;
		this.codec = codec;
	}

	/**
	 * Defines possibility of storing {@code null} values.
	 * <p>
	 * Default is <code>true</code>
	 * @param allowNullValues - stores if <code>true</code>
	 */
	public void setAllowNullValues(boolean allowNullValues) {
		this.allowNullValues = allowNullValues;
	}

	/**
	 * Defines 'fixed' cache names. A new cache instance will not be created in dynamic
	 * for non-defined names.
	 * <p>
	 * `null` parameter setups dynamic mode
	 * @param names of caches
	 */
	public void setCacheNames(Collection<String> names) {
		if (names != null) {
			for (String name : names) {
				getCache(name);
			}
			dynamic = false;
		}
		else {
			dynamic = true;
		}
	}

	/**
	 * Set cache config location
	 * @param configLocation object
	 */
	public void setConfigLocation(String configLocation) {
		this.configLocation = configLocation;
	}

	/**
	 * Set cache config mapped by cache name
	 * @param config object
	 */
	public void setConfig(Map<String, ? extends CacheConfig> config) {
		this.configMap = (Map<String, CacheConfig>) config;
	}

	/**
	 * Set Redisson instance
	 * @param redisson instance
	 */
	public void setRedisson(RedissonClient redisson) {
		this.redisson = redisson;
	}

	/**
	 * Set Codec instance shared between all Cache instances
	 * @param codec object
	 */
	public void setCodec(Codec codec) {
		this.codec = codec;
	}

	protected CacheConfig createDefaultConfig() {
		CacheConfig config = new CacheConfig(globalConfig.getTTL(), globalConfig.getMaxIdleTime());
		config.setMaxSize(globalConfig.getMaxSize());
		return config;
	}

	protected CacheConfig getAndSaveConfig(String cacheName) {
		// TTL 优先级 : 配置文件指定 > 缓存名称TTL后缀 > 默认配置
		CacheConfig config = configMap.get(cacheName);
		if (config == null) {
			config = createDefaultConfig();
			Duration duration = CacheUtil.parseTtl(cacheName).orElse(null);
			if (duration != null) {
				config.setTTL(duration.toMillis());
			}
			configMap.put(cacheName, config);
		}
		return config;
	}

	@Override
	public Cache getCache(String name) {
		Cache cache = instanceMap.get(name);
		if (cache != null) {
			return cache;
		}
		if (!dynamic) {
			return cache;
		}

		CacheConfig config = getAndSaveConfig(name);
		if (config.getMaxIdleTime() == 0 && config.getTTL() == 0 && config.getMaxSize() == 0) {
			return createMap(name, config);
		}

		return createMapCache(name, config);
	}

	private Cache createMap(String name, CacheConfig config) {
		RMap<Object, Object> map = getMap(name, config);

		Cache cache = new RedissonCache(map, allowNullValues);
		Cache oldCache = instanceMap.putIfAbsent(name, cache);
		if (oldCache != null) {
			cache = oldCache;
		}
		return cache;
	}

	protected RMap<Object, Object> getMap(String name, CacheConfig config) {
		if (codec != null) {
			return redisson.getMap(name, codec);
		}
		return redisson.getMap(name);
	}

	private Cache createMapCache(String name, CacheConfig config) {
		RMapCache<Object, Object> map = getMapCache(name, config);

		Cache cache = new RedissonCache(map, config, allowNullValues);
		Cache oldCache = instanceMap.putIfAbsent(name, cache);
		if (oldCache != null) {
			cache = oldCache;
		}
		else {
			map.setMaxSize(config.getMaxSize());
		}
		return cache;
	}

	protected RMapCache<Object, Object> getMapCache(String name, CacheConfig config) {
		if (codec != null) {
			return redisson.getMapCache(name, codec);
		}
		return redisson.getMapCache(name);
	}

	@Override
	public Collection<String> getCacheNames() {
		return Collections.unmodifiableSet(configMap.keySet());
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (configLocation == null) {
			return;
		}

		Resource resource = resourceLoader.getResource(configLocation);
		try {
			this.configMap = (Map<String, CacheConfig>) CacheConfig.fromJSON(resource.getInputStream());
		}
		catch (IOException e) {
			// try to read yaml
			try {
				this.configMap = (Map<String, CacheConfig>) CacheConfig.fromYAML(resource.getInputStream());
			}
			catch (IOException e1) {
				throw new BeanDefinitionStoreException(
						"Could not parse cache configuration at [" + configLocation + "]", e1);
			}
		}
	}

	public void setGlobalConfig(CacheConfig globalConfig) {
		this.globalConfig = globalConfig;
	}

}
