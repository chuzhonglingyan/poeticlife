package com.yuntian.poeticlife.config.shrio.cache;

import com.yuntian.poeticlife.config.shrio.ShrioRedisUtil;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: yuntian
 * @Date: 2019/6/2 0002 18:18
 * @Description: ShiroRedisCache管理类
 */
@Slf4j
public class ShiroRedisCache<K, V> implements Cache<K, V> {

    private String prefix;
    private long expireTime;

    private ShrioRedisUtil shrioRedisUtil;

    public String getPrefix() {
        return prefix;
    }


    public ShiroRedisCache(ShrioRedisUtil shrioRedisUtil, long expireTime, String prefix, String principalIdFieldName) {
        this.shrioRedisUtil = shrioRedisUtil;
        this.prefix = prefix;
        this.expireTime = expireTime;
    }


    private String getRedisCacheKey(K key) {
        if (key == null) {
            return null;
        }
        return getPrefix() + getStringRedisKey(key);
    }


    private String getStringRedisKey(K key) {
        return key.toString();
    }


    @Override
    public V get(K k) throws CacheException {
        log.debug("get key [{}]", k);
        if (k == null) {
            return null;
        }
        String redisCacheKey = getRedisCacheKey(k);
        Object rawValue = shrioRedisUtil.get(redisCacheKey);
        if (rawValue == null) {
            return null;
        }
        return (V) rawValue;
    }


    @Override
    public V put(K key, V value) throws CacheException {
        log.debug("put key [{}]", key);
        if (key == null) {
            log.warn("Saving a null key is meaningless, return value directly without call Redis.");
            return value;
        }
        String redisCacheKey = getRedisCacheKey(key);
        shrioRedisUtil.set(redisCacheKey, value, expireTime);
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
        log.debug("remove key [{}]", key);
        if (key == null) {
            return null;
        }
        String redisCacheKey = getRedisCacheKey(key);
        Object rawValue = shrioRedisUtil.get(redisCacheKey);
        V previous = (V) rawValue;
        shrioRedisUtil.del(redisCacheKey);
        return previous;
    }

    @Override
    public void clear() throws CacheException {
        log.debug("clear cache");
        Set<String> keys = null;
        try {
            keys = shrioRedisUtil.scan(getPrefix() + "*");
        } catch (Exception e) {
            log.error("get keys error", e);
        }
        if (keys == null || keys.size() == 0) {
            return;
        }
        for (String key : keys) {
            shrioRedisUtil.del(key);
        }
    }

    @Override
    public int size() {
        Long longSize = 0L;
        try {
            longSize = shrioRedisUtil.scanSize(getPrefix() + "*");
        } catch (Exception e) {
            log.error("get keys error", e);
        }
        return longSize.intValue();
    }

    @Override
    public Set<K> keys() {
        Set<String> keys = shrioRedisUtil.scan(getPrefix() + "*");
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        Set<K> convertedKeys = new HashSet<K>();
        for (String key : keys) {
            try {
                convertedKeys.add((K) key);
            } catch (Exception e) {
                log.error("deserialize keys error", e);
            }
        }
        return convertedKeys;
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = keys();
        List<V> values = new ArrayList<>(keys.size());
        for (K k : keys) {
            V value = get(k);
            if (value != null) {
                values.add(get(k));
            }
        }
        return values;
    }


}
