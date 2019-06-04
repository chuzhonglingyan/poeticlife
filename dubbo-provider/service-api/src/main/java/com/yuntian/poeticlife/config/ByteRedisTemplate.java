package com.yuntian.poeticlife.config;

/**
 * @Auther: yuntian
 * @Date: 2019/6/2 0002 22:09
 * @Description:
 */

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 重写序列化 序列化为字节码
 * zhw
 */
public class ByteRedisTemplate<K, V> extends RedisTemplate<K, V> {


    /**
     * Constructs a new <code>StringRedisTemplate</code> instance. {@link #setConnectionFactory(RedisConnectionFactory)}
     * and {@link #afterPropertiesSet()} still need to be called.
     */
    public ByteRedisTemplate() {
        setKeySerializer(RedisSerializer.string());
        setValueSerializer(new ByteRedisSerializer());
        setHashKeySerializer(RedisSerializer.string());
        setHashValueSerializer(new ByteRedisSerializer());
    }

    /**
     * Constructs a new <code>StringRedisTemplate</code> instance ready to be used.
     *
     * @param connectionFactory connection factory for creating new connections
     */
    public ByteRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }

}
