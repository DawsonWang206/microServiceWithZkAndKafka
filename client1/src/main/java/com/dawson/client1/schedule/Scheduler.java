package com.dawson.client1.schedule;

import com.dawson.client1.feign.Feign1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    @Autowired
    private Feign1 feign;
    @Scheduled(cron = "4/4 * * * * ?")
    public void  scheduler() {
        String helloFrClient1ByFeign = feign.doHello();
        System.out.println("客户端Feign调用结果," + helloFrClient1ByFeign);

    }
}
