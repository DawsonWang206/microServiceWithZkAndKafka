package com.dawson.client1.kfklistener;

import com.dawson.client1.common.ClientConstant;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestListener {
    @KafkaListener(topics = ClientConstant.TEST_KAFKA_TOPIC, groupId = "client")
    public void listenString(String hello){
        System.out.println("Kafka listener: " + hello);
    }

}
