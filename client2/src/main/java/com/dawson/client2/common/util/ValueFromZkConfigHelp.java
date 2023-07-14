package com.dawson.client2.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class ValueFromZkConfigHelp {
    @Autowired
    private ValueFromZkConfig valueFromZkConfig;
    @PreDestroy
    public void preDestroy(){
        System.out.println("====================ValueFromZkConfigHelp===========");
        valueFromZkConfig.getFromRemote();
    }
}
