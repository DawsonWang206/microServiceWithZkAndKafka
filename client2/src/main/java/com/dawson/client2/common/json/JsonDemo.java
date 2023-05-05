package com.dawson.client2.common.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class JsonDemo {
    public static void main(String[] args) throws Exception {
        String json = "{\"username\":\"tom\",\"company\":{\"companyName\":\"中华\",\"address\":\"北京\"},\"cars\":[\"奔驰\",\"宝马\"]}";
        String arrayJson = "[{\"number\":64,\"result\":\"SUCCESS\"},{\"number\":65,\"result\":\"FAILURE\"},{\"number\":66,\"result\":\"ABORTED\"},{\"number\":67,\"result\":\"SUCCESS\"}]";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(arrayJson);
//        Iterator<String> it = jsonNode.fieldNames();
//        while (it.hasNext()) System.out.println(it.next());
//        System.out.println(jsonNode.path("username"));
//        System.out.println(jsonNode.findValue("username"));
//        System.out.println(jsonNode.findPath("username"));
        jsonNode.forEach(jsonNode1 -> {
            System.out.println("结果:" + jsonNode1.toString());
        });
        String jsonStr = objectMapper.writeValueAsString(jsonNode);
        System.out.println(jsonStr);
        JSONObject object = new JSONObject("");
        JSONArray jsonArray = new JSONArray("");
    }
}
