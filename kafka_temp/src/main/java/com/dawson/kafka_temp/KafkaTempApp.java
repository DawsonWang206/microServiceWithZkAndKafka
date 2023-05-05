package com.dawson.kafka_temp;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication
public class KafkaTempApp {
    public static void main(String[] args) {
        SpringApplication.run(KafkaTempApp.class, args);
    }
    @Bean
    public ApplicationRunner runner(){
        return args -> {
            System.out.println("启动成功");
        };
    }
}
