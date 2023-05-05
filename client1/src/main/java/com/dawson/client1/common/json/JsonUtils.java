package com.dawson.client1.common.json;

import com.dawson.client1.common.bean.ApplicationContextUtils;
import com.dawson.client1.common.response.RRException;
import com.dawson.client1.common.response.RRExceptionEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


public class JsonUtils  {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final JsonConfig jsonConfig = ApplicationContextUtils.getBean(JsonConfig.class);
    static {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(jsonConfig.getLocalDateTimePattern());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(jsonConfig.getLocalDatePattern());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(jsonConfig.getLocalTimePattern());
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        JavaTimeModule timeModule = new JavaTimeModule();
        //日期序列化
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        //日期反序列化
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        OBJECT_MAPPER.registerModule(timeModule);
        OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone(jsonConfig.getTimeZone()));
    }

    public static String toJsonString(Object object) {
        if (object == null)
            return null;
        try{
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RRException(RRExceptionEnum.JSON_ERROR, e.getMessage());
        }

    }
    public static String toJsonString(JsonArray jsonArray){
        if (null == jsonArray)
            return null;
        return jsonArray.toString();
    }

    public static String toJsonString(JsonObject jsonObject) {
        if(null == jsonObject)
            return null;
        return jsonObject.toString();
    }

    public static <T> T toObject(String json, Class<T> tClass){
        if (Strings.isEmpty(json)) return null;
        try {
            return OBJECT_MAPPER.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RRException(RRExceptionEnum.JSON_ERROR, e.getMessage());
        }
    }

    public static <T> T toObject(String json, TypeReference<T> tTypeReference) {
        if(Strings.isEmpty(json))
            return null;
        try {
            return OBJECT_MAPPER.readValue(json, tTypeReference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RRException(RRExceptionEnum.JSON_ERROR, e.getMessage());
        }
    }
    public static JsonObject toJsonObject(String json) {
        return new JsonObject(toObject(json, new TypeReference<Map<String, JsonNode>>(){}));
    }

    public static JsonObject toJsonObject(JsonNode node) {
        return new JsonObject(toObject(toJsonString(node), new TypeReference<Map<String, JsonNode>>(){}));
    }

    public static JsonArray toJsonArray(String json) {
        return new JsonArray(toObject(json, new TypeReference<List<JsonNode>>(){}));
    }

    public static JsonArray toJsonArray(JsonNode jsonNode) {
        return new JsonArray(toObject(toJsonString(jsonNode), new TypeReference<List<JsonNode>>() {
        }));
    }

    public static JsonNode toJsonNode(Object object) {
        String json = toJsonString(object);
        if (Strings.isEmpty(json))
            return null;
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RRException(RRExceptionEnum.JSON_ERROR, e.getMessage());
        }
    }



}
