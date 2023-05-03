package com.dawson.client2.feign;

import com.dawson.client2.dto.entity.CustomEntitySample;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "cloud-client1")
@RequestMapping("/feign")
public interface Fein1 {
    @GetMapping("/hello")
    String feinHello();

    @GetMapping("/id")
    CustomEntitySample feinSeekEntity(Integer id);

    ResponseEntity<Object> doEntity(CustomEntitySample sample);


}
