package com.dawson.client2.common.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Data
@RefreshScope
public class ValueFromZkConfig {
    @Value("${fromRemote}")
    private String fromRemote;
    @PreDestroy
    public void printString() {
        System.out.println("===============From remote=======================");
        System.out.println(fromRemote);
    }

}
