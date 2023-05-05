package com.dawson.client2.common.redis;


import com.dawson.client2.common.json.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.mapping.MappingException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Cache {
    private static com.dawson.client2.common.bean.ApplicationContextUtils ApplicationContextUtils;
    private static final RedisUtils redis = ApplicationContextUtils.getBean(RedisUtils.class);
    private static final String STRING_EMPTY = "empty";

    public static <T> T cache(String key, Class<T> tClass, CacheAdapter<T> adapter) throws JsonProcessingException{
        return cache(key, tClass, RedisUtils.NOT_EXPIRE, false, adapter);
    }

    public static  <T> T cache(String key, TypeReference<T> tTypeReference, CacheAdapter<T> adapter) throws JsonProcessingException {
        return cache(key, tTypeReference, RedisUtils.NOT_EXPIRE, false, adapter);
    }

    public static <T> T cache(String key, Class<T> tClass, CacheAdapterWithParam<T> adapterWithParam,
                              Map<String, Object> params) throws JsonProcessingException {
        return cache(key, tClass, RedisUtils.NOT_EXPIRE, false, adapterWithParam, params);
    }

    public static <T> T cache(String key, Class<T> tClass, long expire, CacheAdapter<T> cacheAdapter)
            throws JsonProcessingException{
        return cache(key, tClass, expire, false, cacheAdapter);
    }

    public static <T> T cache(String key, TypeReference<T> tTypeReference, long expire, CacheAdapter<T> cacheAdapter)
            throws JsonProcessingException{
        return cache(key, tTypeReference, expire, false, cacheAdapter);
    }

    public static <T> T cache(String key, Class<T> tClass, long expire, CacheAdapterWithParam<T> adapter,
                              Map<String, Object> params) throws JsonProcessingException {
        return cache(key, tClass, expire, false, adapter, params);
    }

    public static <T> T cache(String key, TypeReference<T> tTypeReference, long expire,
                              CacheAdapterWithParam<T> adapterWithParam, Map<String,Object> params) throws JsonProcessingException {
        return cache(key, tTypeReference, expire, false, adapterWithParam, params);
    }

    public static <T> T cache(String key, Class<T> tClass, long expire, boolean saveEmpty, CacheAdapter<T> adapter)
        throws JsonProcessingException, MappingException {
        String jsonString = selectString(key);
        if(Strings.isNotBlank(jsonString))
            return saveEmpty && jsonString.equals(STRING_EMPTY) ? null : JsonUtils.toObject(jsonString, tClass);
        return selectAndCache(adapter, key, expire, saveEmpty);
    }

    public static <T> T cache(String key, TypeReference<T> tTypeReference, long expire,
                              boolean saveEmpty, CacheAdapter<T> adapter) throws JsonProcessingException {
        String jsonString = selectString(key);
        if(Strings.isNotBlank(jsonString))
            return saveEmpty && jsonString.equals(STRING_EMPTY) ? null : JsonUtils.toObject(jsonString, tTypeReference);
        return selectAndCache(adapter, key, expire, saveEmpty);
    }

    public static <T> T cache (String key, Class<T> tClass, long expire, boolean saveEmpty,
                               CacheAdapterWithParam<T> adapter, Map<String, Object> params) throws JsonProcessingException {
        String jsonString = selectString(key);
        if(Strings.isNotBlank(jsonString))
            return saveEmpty && jsonString.equals(STRING_EMPTY) ? null : JsonUtils.toObject(jsonString, tClass);
        return selectAndCache(adapter, params, key, expire, saveEmpty);
    }
    public static <T> T cache(String key, TypeReference<T> tTypeReference, long expire, boolean saveEmpty,
                              CacheAdapterWithParam<T> adapter, Map<String, Object> params) throws JsonProcessingException{
        String jsonString = selectString(key);
        if(Strings.isNotBlank(jsonString))
            return saveEmpty && jsonString.equals(STRING_EMPTY) ? null : JsonUtils.toObject(jsonString, tTypeReference);
        return selectAndCache(adapter, params,key, expire, saveEmpty);

    }

    private static <T> T  selectAndCache(CacheAdapter<T> adapter, String key, long expire, boolean saveEmpty) {
            T t = adapter.selectFromDB();
            save(key, t, expire, saveEmpty);
            return t;
    }
    private static <T> T selectAndCache(CacheAdapterWithParam<T> adapterWithParam, Map<String, Object> params,
                                        String key, long expire, boolean saveEmpty) {
        T t = adapterWithParam.selectFromDB(params);
        save(key, t, expire, saveEmpty);
        return t;
    }
    private static <T> void save(String key, T t, long expire, boolean saveEmpty) {
        if (null != t) {
            if ((t instanceof String && Strings.isBlank((String) t)) ||
            t instanceof Collection && ((Collection)t).isEmpty() || (t instanceof Map && ((Map)t).isEmpty())) {
                if(saveEmpty) {
                    insertString(key, STRING_EMPTY);
                }
            }
            else {
                insertObject(key, t, expire);
            }

        }
    }

    // =================RedisUtils方法静态封装======================

    public static void insertString(String key, String value) {
        redis.insertString(key, value);
    }
    public static void insertString(String key, String value, long expired) {
        redis.insertString(key, value, expired);
    }
    public static void insertObject(String key, Object object) {
        redis.insertObject(key, object);
    }
    public static void insertObject(String key, Object object, long expired) {
        redis.insertObject(key, object, expired);
    }
    public static void updateString(String key, String value) {
        redis.updateString(key, value);
    }
    public static void updateObject(String key, Object value) {
        redis.updateObject(key, value);
    }
    public  static String selectString(String key) {
        return redis.selectString(key);
    }
    public static String selectString(String key, long expire) {
        return redis.selectString(key, expire);
    }
    public static <T> T selectObject(String key, Class<T> tClass) throws JsonProcessingException{
        return redis.selectObject(key, tClass);
    }
    public static <T> T selectObject(String key, Class<T> tClass, long expire) throws JsonProcessingException {
        return redis.selectObject(key, tClass, expire);
    }
    public static <T> T selectObject(String key, TypeReference<T> tTypeReference) throws JsonProcessingException{
        return redis.selectObject(key, tTypeReference);
    }
    public static <T> T selectObeject(String key, TypeReference<T> tTypeReference, long expire)
            throws JsonProcessingException {
        return redis.selectObject(key, tTypeReference, expire);
    }
    public static boolean delete(String key) {
        return redis.delete(key);
    }
    public static Set<String> keySet(String key) {
        return redis.keySet(key);
    }
    public static List<String> selectBatch(Set<String> keySet) {
        return redis.selectBatch(keySet);
    }
    public static List<String> selectBatch(String key) {
        return  redis.selectBatch(key);
    }
    public static boolean deleteBatch(String key) {
        return redis.delete(key);
    }
    public static boolean deleteBatch(Set<String> keySet) {
        return redis.deleteBatch(keySet);
    }
    public static boolean setIfAbsent(String key, String value, long expire) {
        return redis.setIfAbsent(key, value, expire);
    }
    public static Long expire(String key) {
        return redis.getExpire(key);
    }


}
