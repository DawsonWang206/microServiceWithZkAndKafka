package com.dawson.client2.common.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JsonObject implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, JsonNode> map;
    public JsonObject(int initCapacity, float loadFactor){
        map = new HashMap<>(initCapacity, loadFactor);
    }
    public JsonObject(int initCapacity){
        map = new HashMap<>(initCapacity);
    }
    public JsonObject(){
        map = new HashMap<>();
    }
    public JsonObject(Map<String, JsonNode> map) {
        this.map = map;
    }
    public String getString(String key) {
        if (Strings.isEmpty(key))
            return null;
        JsonNode jsonNode = map.get(key);
        return jsonNode == null ? null :jsonNode.textValue();
    }

    public Integer getInteger(String key){
        if (Strings.isEmpty(key))
            return null;
        JsonNode jsonNode = map.get(key);
        return jsonNode == null ? null :jsonNode.intValue();
    }

    public Long getLong(String key){
        if (Strings.isEmpty(key))
            return null;
        JsonNode jsonNode = map.get(key);
        return jsonNode == null ? null :jsonNode.longValue();
    }
    public Double getDouble(String key){
        if (Strings.isEmpty(key))
            return null;
        JsonNode jsonNode = map.get(key);
        return jsonNode == null ? null :jsonNode.doubleValue();
    }

    public  Boolean getBoolean(String key){
        if (Strings.isEmpty(key))
            return null;
        JsonNode jsonNode = map.get(key);
        return jsonNode == null ? null :jsonNode.booleanValue();
    }

    public JsonObject getJsonObject(String key){
        return JsonUtils.toJsonObject(getString(key));
    }
    public JsonArray getJsonArray(String key) {
        return JsonUtils.toJsonArray(getString(key));
    }

    public <T> T getObject(String key, Class<T> tClass) {
        return JsonUtils.toObject(getString(key), tClass);
    }
    public <T> T getObject(String key, TypeReference<T> tTypeReference) {
        return JsonUtils.toObject(key, tTypeReference);
    }
    public JsonObject put(String key, Object value) {
        if (Strings.isEmpty(key)) throw  new RuntimeException("key不能为空");
        map.put(key, JsonUtils.toJsonNode(value));
        return this;
    }
    public String toString(){
        return JsonUtils.toJsonString(map);
    }
}
