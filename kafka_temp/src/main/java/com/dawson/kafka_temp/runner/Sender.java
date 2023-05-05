package com.dawson.kafka_temp.runner;

import com.dawson.kafka_temp.config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

public class Sender {
    private final KafkaTemplate<Integer, String> kafkaTemplate;
    public static void main(String[] args){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        context.getBean(Sender.class).send("test", 42);

    }
    public Sender(KafkaTemplate<Integer, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void send(String toSend, int key) {
        kafkaTemplate.send("topic1", key, toSend);
    }

}
