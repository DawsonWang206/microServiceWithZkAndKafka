package com.dawson.client2.common.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JsonArray implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    @Getter
    private List<JsonNode>  list;

    public JsonArray() {
        list = new ArrayList<>();
    }
    public JsonArray(int initialCapacity) {
        list = new ArrayList<>(initialCapacity);
    }

    public JsonArray(List<JsonNode> c) {
        list = new ArrayList<>(c);
    }

    public JsonArray(JsonArray c) {
        list = new ArrayList<>(c.getList());
    }
    public String getString(int ind) {
        JsonNode node = list.get(ind);
        return null == node ? null : node.textValue();
    }

    /**
     * 取出指定索引处的JsonNode元素，并转为Integer
     *
     * @param ind
     * @return
     */
    public Integer getInteger(int ind) {
        JsonNode node = list.get(ind);
        return null == node ? null : node.intValue();
    }

    /**
     * 取出指定索引处的JsonNode元素，并转为Long
     *
     * @param ind
     * @return
     */
    public Long getLong(int ind) {
        JsonNode node = list.get(ind);
        return null == node ? null : node.longValue();
    }

    /**
     * 取出指定索引处的JsonNode元素，并转为Double
     *
     * @param ind
     * @return
     */
    public Double getDouble(int ind) {
        JsonNode node = list.get(ind);
        return null == node ? null : node.doubleValue();
    }

    /**
     * 取出指定索引处的JsonNode元素，并转为Boolean
     *
     * @param ind
     * @return
     */
    public Boolean getBoolean(int ind) {
        JsonNode node = list.get(ind);
        return null == node ? null : node.booleanValue();
    }

    /**
     * 取出指定索引处的JsonNode元素，并转为JsonObject
     *
     * @param ind
     * @return
     */
    public JsonObject getJsonObject(int ind) {
        return JsonUtils.toJsonObject(list.get(ind));
    }

    /**
     * 取出指定索引处的JsonNode元素，并转为JsonArray
     *
     * @param ind
     * @return
     */
    public JsonArray getJsonArray(int ind) {
        return JsonUtils.toJsonArray(list.get(ind));
    }

    /**
     * 取出指定索引处的JsonNode元素，并转为指定类型的对象
     *
     * @param ind
     * @param tClass
     * @return
     */
    public <T> T getObject(int ind, Class<T> tClass) {
        return JsonUtils.toObject(getString(ind), tClass);
    }

    /**
     * 取出指定索引处的JsonNode元素，并转为指定类型的对象
     *
     * @param ind
     * @param tTypeRef
     * @return
     */
    public <T> T getObject(int ind, TypeReference<T> tTypeRef) {
        return JsonUtils.toObject(getString(ind), tTypeRef);
    }

    /**
     * 添加任意对象
     *
     * @param obj
     * @return
     */
    public boolean addObject(Object obj) {
        return list.add(JsonUtils.toJsonNode(obj));
    }

    /**
     * 向指定索引处添加任意对象
     *
     * @param ind
     * @param obj
     * @return
     */
    public JsonNode setObject(int ind, Object obj) {
        return list.set(ind, JsonUtils.toJsonNode(obj));
    }

    public boolean addAll(JsonArray c) {
        return list.addAll(c.list);
    }

    public boolean addAll(int index, JsonArray c) {
        return list.addAll(index, c.list);
    }

    @Override
    public String toString() {
        return JsonUtils.toJsonString(list);
    }

    public boolean contains(Object o) {
        return list.contains(JsonUtils.toJsonNode(o));
    }

}
