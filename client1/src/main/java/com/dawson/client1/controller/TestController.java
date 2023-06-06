package com.dawson.client1.controller;

import com.dawson.client1.common.response.R;
import com.dawson.client1.dto.entity.CustomEntitySample;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Value("${spring.application.name:client1}")
    private String appName;
    @GetMapping("/hello")
    public String hello(){
        return String.format("Hello, this is from %s",appName);
    }

    @GetMapping("/id")
    public CustomEntitySample seekEntity(Integer id){
        CustomEntitySample entity = new CustomEntitySample();
        entity.setId(id);
        entity.setName("test");
        entity.setDescription("Entity from client1");
        return entity;
    }
    @PostMapping("/entity")
    public R doEntity(CustomEntitySample entitySample) {
        return R.ok().put("entity", entitySample);
    }

}
