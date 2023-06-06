package com.dawson.client2.scheduler;

import com.dawson.client2.common.response.R;
import com.dawson.client2.dto.entity.CustomEntitySample;
import com.dawson.client2.feign.Feign1;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestScheduler {
    @Autowired
    private Feign1 feign;

    @Scheduled(cron = "4/4 * * * * ?")
    public void doHello(){
        String helloFrClient1ByFeign = feign.feinHello();

        System.out.println("客户端Feign调用结果," + helloFrClient1ByFeign);
    }

    @Scheduled(cron = "6/6 * * * * ?")
    public CustomEntitySample feinSeekEntityScheduler() throws Exception {
        int randomInt = RandomUtils.nextInt();
        CustomEntitySample entitySample = feign.feinSeekEntity(randomInt);
        System.out.println("Feign调用seekEntity结果：" + new ObjectMapper().writeValueAsString(entitySample));
        return entitySample;
    }
    @Scheduled(cron = "6/6 * * * * ?")
    public R feinDoEntity() throws Exception {
        CustomEntitySample sample = feinSeekEntityScheduler();
        System.out.println("Feign调用doEntity开始：" + new ObjectMapper().writeValueAsString(sample));
        R response = feign.doEntity(sample);
        System.out.println("Feign调用doEntity结果："+ new ObjectMapper().writeValueAsString(response));
        return feign.doEntity(sample);
    }



}
