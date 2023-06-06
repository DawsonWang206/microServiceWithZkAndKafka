package com.dawson.client1.kfklistener;

import com.dawson.client1.common.ClientConstant;
import com.dawson.client1.common.json.JsonUtils;
import com.dawson.client1.common.response.R;
import com.dawson.client1.dto.entity.CustomEntitySample;
import org.apache.kafka.common.message.ConsumerProtocolAssignment;
import org.apache.kafka.common.message.ConsumerProtocolSubscription;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TestListener {
    @KafkaListener(topics = ClientConstant.TEST_KAFKA_TOPIC, groupId = "client", containerFactory = "stringKafkaListenerContainerFactory")
    public void listenString(@Payload String hello, @Header(KafkaHeaders.OFFSET) Integer offset){
        System.out.println("Kafka listener1: " + hello + ". offset:" + offset );
    }

    @KafkaListener(topics = ClientConstant.TEST_KAFKA_OBJECT_TOPIC1, groupId = "client",
            containerFactory = "customEntitySampleKafKaListenerContainerFactory")
    public void listenCustomEntitySample(@Payload CustomEntitySample customEntitySample,
                                         @Header(KafkaHeaders.OFFSET) Integer offset){
        System.out.println("Kafka listener2: " + JsonUtils.toJsonString(customEntitySample) +
                ". offset:" + offset);
    }

    @KafkaListener(topics = ClientConstant.TEST_KAFKA_OBJECT_TOPIC2, groupId = "client",
            containerFactory = "rKafKaListenerContainerFactory",errorHandler = "consumerErrHandler")
    public void listenR(String in, @Header(KafkaHeaders.OFFSET) Integer offset){
        System.out.println("Kafka listener3: " + JsonUtils.toJsonString(in) + ". offset:" + offset);
    }

}
