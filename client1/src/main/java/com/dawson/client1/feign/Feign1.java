package com.dawson.client1.feign;

import com.dawson.client1.dto.entity.CustomEntitySample;
import feign.HeaderMap;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cloud-client2", path = "/client2/api")
public interface Feign1 {
    @GetMapping("/test/hello")
    String doHello();

    @RequestMapping(path = "/test/id", method = RequestMethod.GET)
    CustomEntitySample feinSeekEntity(@RequestParam Integer id);

    @PostMapping("/test/entity")
    ResponseEntity<Object> doEntity(CustomEntitySample sample);


}
