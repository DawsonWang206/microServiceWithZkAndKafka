package com.dawson.kafka_temp.listener;

import org.springframework.kafka.annotation.KafkaListener;

public class Listener {
    @KafkaListener(id = "listener1", topics = "topic1")
    public void listener1(String in) {
        System.out.println(in);
    }
}
