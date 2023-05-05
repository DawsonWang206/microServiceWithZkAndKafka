package com.dawson.client1.common.redis;

import com.dawson.client1.common.json.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, String> valueOperations;
    private final HashOperations<String, String, Object> hashOperations;
    private final ListOperations<String, Object> listOperations;
    private final SetOperations<String, Object> setOperations;
    private final ZSetOperations<String, Object> zSetOperations;

    public final static long NOT_EXPIRE = -1;
    public final static long NOT_EXIST = -2;

    @Autowired
    public RedisUtils(RedisTemplate<String, Object> redisTemplate, ValueOperations<String, String> valueOperations,
                      HashOperations<String, String, Object> hashOperations, ListOperations<String, Object> listOperations,
                      SetOperations<String, Object> setOperations, ZSetOperations<String, Object> zSetOperations) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = valueOperations;
        this.hashOperations = hashOperations;
        this.listOperations = listOperations;
        this.setOperations = setOperations;
        this.zSetOperations = zSetOperations;
    }

    public synchronized  void  insertString(String key, String value){
        insertString(key, value, NOT_EXPIRE);
    }

    public synchronized void insertString(String key, String value, long expire) {
        valueOperations.set(key, value);
        if (expire != NOT_EXPIRE)
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }
    public synchronized void insertObject(String key, Object value){
        insertObject(key, value, NOT_EXPIRE);
    }
    public synchronized  void insertObject(String key, Object value, long expire){
        String jsonValue = JsonUtils.toJsonString(value);
        System.out.println(jsonValue);
//        String jsonValue = null;
//       try {
//           System.out.println(((LocalDateTime)value).getYear());
//           ObjectMapper objectMapper = new ObjectMapper();
////           objectMapper.findAndRegisterModules();
////           jsonValue = objectMapper.writeValueAsString(value);
//           jsonValue = new ObjectMapper().writeValueAsString(value);
//       } catch (JsonProcessingException e) {
//           e.printStackTrace();
//       }
       insertString(key, jsonValue, expire);

    }
    public synchronized void updateString(String key, String value){
        Long expire = getExpire(key);
        if (NOT_EXPIRE != expire)
            insertString(key, value);

    }

    public synchronized void updateObject(String key, Object value) {
        Long expire =getExpire(key);
        if (expire != NOT_EXPIRE)
            insertObject(key, value, expire);
    }

    public synchronized String selectString(String key) {
        return selectString(key, NOT_EXPIRE);
    }

    public  synchronized String selectString(String key, long expire) {
        String value =valueOperations.get(key);
        if(expire != NOT_EXPIRE && Strings.isNotBlank(value)) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }


    public synchronized <T> T selectObject(String key, Class<T> tClass) throws JsonProcessingException{
        return selectObject(key, tClass, NOT_EXPIRE);
    }

    public synchronized <T> T selectObject(String key, Class<T> tClass, long expire) throws JsonProcessingException {
        String json =selectString(key, expire);
        return JsonUtils.toObject(json, tClass);
    }
    public synchronized <T> T selectObject(String key, TypeReference<T> tTypeReference)
            throws JsonMappingException, JsonProcessingException {
        return selectObject(key, tTypeReference, NOT_EXPIRE);

    }

    public synchronized <T> T selectObject(String key, TypeReference<T> tTypeReference, long expire)
        throws JsonMappingException, JsonProcessingException {
        String jsonValue = selectString(key, expire);
        return JsonUtils.toObject(key, tTypeReference);
    }

    public synchronized boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public synchronized Set<String> keySet(String key){
        return redisTemplate.keys(key);
    }

    public synchronized List<String> selectBatch(Set<String> keySet) {
        return valueOperations.multiGet(keySet);
    }

    public synchronized List<String> selectBatch(String key) {
        return valueOperations.multiGet(redisTemplate.keys(key));
    }

    public synchronized boolean deleteBatch(Set<String> keySet) {
        return redisTemplate.delete(keySet) >= 0;
    }
    public synchronized boolean deleteBatch(String key) {
        return redisTemplate.delete(redisTemplate.keys(key)) >= 0 ;
    }

    public synchronized boolean setIfAbsent(String key, String value, Long expire) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.SECONDS);
    }

    public synchronized Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


}
