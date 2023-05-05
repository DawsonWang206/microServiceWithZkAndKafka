package com.dawson.client1.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaUtils {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

}
