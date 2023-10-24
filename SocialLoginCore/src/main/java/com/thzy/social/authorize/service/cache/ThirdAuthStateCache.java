package com.thzy.social.authorize.service.cache;

/**
 * @className: ThirdAuthStateCache
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
public interface ThirdAuthStateCache {

    /**
     * 使用一次就删除
     */
    String STATE_USE_ONCE = "STATE_USE_ONCE";

    /**
     * 指定时间后删除
     */
    String STATE_USE_IN_TIME = "STATE_USE_IN_TIME";

    /**
     * state默认过去时间
     */
    long defaultExpireTime = 5;

    /**
     * 缓存
     *
     * @param key   键
     * @param value 值
     */
    void cache(String key, String value);

    void cache(String tag, String key, String value);

    /**
     * 缓存，带过期时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 键过期时间
     */
    void cache(String key, String value, long timeout);

    /**
     * 缓存，带过期时间
     *
     * @param tag     标志位
     * @param key     键
     * @param value   值
     * @param timeout 键过期时间
     */
    void cache(String tag, String key, String value, long timeout);

    /**
     * 获取指定值
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 获取指定值
     *
     * @param key
     * @return
     */
    String getAndRemove(String key);


    /**
     * 判断指点键是否存在
     *
     * @param key
     * @return
     */
    boolean contains(String key);

    /**
     * 移除指定键值对
     *
     * @param key
     */
    void remove(String key);
}
