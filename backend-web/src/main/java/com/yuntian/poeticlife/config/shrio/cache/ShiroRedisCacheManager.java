package com.yuntian.poeticlife.config.shrio.cache;

import com.yuntian.poeticlife.config.shrio.ShrioRedisUtil;
import com.yuntian.poeticlife.config.shrio.cache.ShiroRedisCache;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: yuntian
 * @Date: 2019/6/2 0002 18:16
 * @Description:
 */
@Slf4j
public class ShiroRedisCacheManager implements CacheManager {

    /**
     * The Redis key prefix for caches
     */
    private static final String DEFAULT_CACHE_KEY_PREFIX = "shiro:cache:";

    public static final String DEFAULT_PRINCIPAL_ID_FIELD_NAME = "authCacheKey or id";

    private String principalIdFieldName=DEFAULT_PRINCIPAL_ID_FIELD_NAME;

    private String keyPrefix;
    private long expireTime;

    private ShrioRedisUtil shrioRedisUtil;

    /**
     * fast lookup by name map
     */
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();


    public ShiroRedisCacheManager(ShrioRedisUtil shrioRedisUtil, String keyPrefix, long expireTime) {
        if (StringUtils.isBlank(keyPrefix)) {
            this.keyPrefix = DEFAULT_CACHE_KEY_PREFIX;
        } else {
            this.keyPrefix = keyPrefix;
        }
        this.shrioRedisUtil = shrioRedisUtil;
        this.expireTime = expireTime;
    }

    public String getPrincipalIdFieldName() {
        return principalIdFieldName;
    }

    public void setPrincipalIdFieldName(String principalIdFieldName) {
        this.principalIdFieldName = principalIdFieldName;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public ShrioRedisUtil getShrioRedisUtil() {
        return shrioRedisUtil;
    }

    public void setShrioRedisUtil(ShrioRedisUtil shrioRedisUtil) {
        this.shrioRedisUtil = shrioRedisUtil;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        Cache cache = caches.get(name);
        if (cache == null) {
            cache = new ShiroRedisCache<K, V>(shrioRedisUtil, expireTime, keyPrefix + name + ":",principalIdFieldName);
            caches.put(name, cache);
        }
        return cache;
    }
}
