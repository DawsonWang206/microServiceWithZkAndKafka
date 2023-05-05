package com.dawson.client2.common.redis;

@FunctionalInterface
public interface CacheAdapter<T> {
    /**
     * 从数据库中查询
     * @return
     */
    T selectFromDB();
}
