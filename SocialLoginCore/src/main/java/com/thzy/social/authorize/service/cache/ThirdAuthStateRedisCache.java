package com.thzy.social.authorize.service.cache;

import com.thzy.social.authorize.manager.CommonPropertyManager;
import com.thzy.social.constant.SocialConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @className: ThirdAuthStateRedisCache
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
@Component
public class ThirdAuthStateRedisCache implements ThirdAuthStateCache {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private ValueOperations<String, String> valueOperations;

    @Autowired
    private CommonPropertyManager commonPropertyManager;

    private long timeout;

    @PostConstruct
    public void init() {
        valueOperations = redisTemplate.opsForValue();
        String expireTime = commonPropertyManager.getValue(SocialConstant.THIRD_AUTH_STATE_CACHE_EXPIRE_TIME);
        if (StringUtils.isBlank(expireTime)) {
            timeout = defaultExpireTime;
        } else {
            timeout = Long.valueOf(expireTime);
        }
    }

    /**
     * 存入缓存，默认3分钟
     *
     * @param key   缓存key
     * @param value 缓存内容
     */
    @Override
    public void cache(String key, String value) {
        valueOperations.set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public void cache(String tag, String key, String value) {
        String tagConnectKeyStr = tag + key;
        valueOperations.set(tagConnectKeyStr, value, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 存入缓存
     *
     * @param key     缓存key
     * @param value   缓存内容
     * @param timeout 指定缓存过期时间（毫秒）
     */
    @Override
    public void cache(String key, String value, long timeout) {
        valueOperations.set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public void cache(String tag, String key, String value, long timeout) {
        String tagConnectKeyStr = tag + key;
        valueOperations.set(tagConnectKeyStr, value, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取缓存内容
     *
     * @param key 缓存key
     * @return 缓存内容
     */
    @Override
    public String get(String key) {
        return valueOperations.get(key);
    }


    /**
     * 是否存在key，如果对应key的value值已过期，也返回false
     *
     * @param key 缓存key
     * @return true：存在key，并且value没过期；false：key不存在或者已过期
     */
    @Override
    public boolean contains(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public String getAndRemove(String key) {
        String value = null;
        try {
            value = this.get(key);
        } finally {
            this.remove(key);
        }
        return value;
    }
}
