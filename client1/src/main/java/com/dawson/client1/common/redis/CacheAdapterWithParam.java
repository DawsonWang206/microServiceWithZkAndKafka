package com.dawson.client1.common.redis;

import java.util.Map;

@FunctionalInterface
public interface CacheAdapterWithParam<T>{

    /**
     * 从数据库中查询
     * @param params
     * @return
     */
    T selectFromDB(Map<String, Object> params);
}
