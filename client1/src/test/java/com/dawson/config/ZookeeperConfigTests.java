package com.dawson.config;

import com.dawson.client1.Client1Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Client1Application.class)
public class ZookeeperConfigTests {
    @Value("${nickname1}")
    private String nickname1;
    @Value("${nickname2}")
    private String nickname2;

    @Test
    public void testZkConfig(){
        System.out.println("从application全局配置中读取配置：nickname1=" + nickname1);
        System.out.println("从application全局配置中读取配置：nickname2=" + nickname2);
    }
}
