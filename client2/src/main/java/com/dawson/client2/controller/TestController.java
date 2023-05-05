package com.dawson.client2.controller;
import com.dawson.client2.dto.entity.CustomEntitySample;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Value("${spring.application.name:client2}")
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
        entity.setDescription("Entity from client2");
        return entity;
    }
    @PostMapping("/entity")
    public ResponseEntity<Object> doEntity(CustomEntitySample entitySample) {
        return ResponseEntity.ok(entitySample);
    }

}
