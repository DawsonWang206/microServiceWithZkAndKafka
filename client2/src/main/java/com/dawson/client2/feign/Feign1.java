package com.dawson.client2.feign;

import com.dawson.client2.dto.entity.CustomEntitySample;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cloud-client1", path = "/client1/api")
public interface Feign1 {
    @GetMapping("/test/hello")
    String feinHello();

    @GetMapping("/test/id")
    CustomEntitySample feinSeekEntity(@RequestParam Integer id);

    @PostMapping("/test/entity")
    ResponseEntity<Object> doEntity(CustomEntitySample sample);


}
