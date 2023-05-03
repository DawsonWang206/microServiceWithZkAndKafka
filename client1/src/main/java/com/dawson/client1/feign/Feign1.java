package com.dawson.client1.feign;

import com.dawson.client1.dto.entity.CustomEntitySample;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "cloud-client2", path = "/client2/api")
public interface Feign1 {
    @GetMapping("/test/hello")
    String doHello();

    @GetMapping("/id")
    CustomEntitySample feinSeekEntity(Integer id);

    @PostMapping("/entity")
    ResponseEntity<Object> doEntity(CustomEntitySample sample);


}
