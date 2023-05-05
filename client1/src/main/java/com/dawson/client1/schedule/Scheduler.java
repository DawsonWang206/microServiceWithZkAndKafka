package com.dawson.client1.schedule;

import com.dawson.client1.common.ClientConstant;
import com.dawson.client1.common.redis.Cache;
import com.dawson.client1.dto.entity.CustomEntitySample;
import com.dawson.client1.feign.Feign1;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;

@Component
public class Scheduler {
    @Autowired
    private Feign1 feign;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private KafkaTemplate<String, CustomEntitySample> customEntitySampleKafkaTemplate;
    @Autowired
    private KafkaTemplate<String, ResponseEntity<Object>> responseEntityKafkaTemplate;

    @PostConstruct
    public void configSerializer() {

    }
    @Scheduled(cron = "4/4 * * * * ?")
    public void  helloScheduler() throws Exception{
        String helloFrClient1ByFeign = feign.doHello();
        Cache.insertString(String.valueOf(System.currentTimeMillis()), helloFrClient1ByFeign, 60 * 5);
        ListenableFuture<SendResult<String, String>> a = kafkaTemplate.send(ClientConstant.TEST_KAFKA_TOPIC, helloFrClient1ByFeign);
        System.out.println("客户端Feign调用结果," + helloFrClient1ByFeign);
        System.out.println("kafka发送消息成功,已发送的topic为：" + a.get().getProducerRecord().topic());
    }

    @Scheduled(cron = "6/6 * * * * ?")
    public CustomEntitySample feinSeekEntityScheduler() throws Exception {
        int randomInt = RandomUtils.nextInt();
        CustomEntitySample entitySample = feign.feinSeekEntity(randomInt);
        Cache.insertObject("entity_sample:" + String.valueOf(System.currentTimeMillis()),
                entitySample, ClientConstant.EXPIRE_FIVE_MINUTES);
        ListenableFuture<SendResult<String, CustomEntitySample>> a = customEntitySampleKafkaTemplate
                .send(ClientConstant.TEST_KAFKA_OBJECT_TOPIC1, entitySample);
        System.out.println("Feign调用seekEntity结果：" + new ObjectMapper().writeValueAsString(entitySample));
        System.out.println("kafka发送消息成功,已发送的topic为：" + a.get().getProducerRecord().topic());
        return entitySample;
    }

    @Scheduled(cron = "6/6 * * * * ?")
    public ResponseEntity<Object> feinDoEntity() throws Exception {
        CustomEntitySample sample = feign.feinSeekEntity(RandomUtils.nextInt());
        Cache.insertObject("entity_sample:" + String.valueOf(System.currentTimeMillis()),
                sample, ClientConstant.EXPIRE_FIVE_MINUTES);
        System.out.println("Feign调用doEntity开始：" + new ObjectMapper().writeValueAsString(sample));
        ResponseEntity<Object> response = feign.doEntity(sample);
        ListenableFuture<SendResult<String, ResponseEntity<Object>  >> a = responseEntityKafkaTemplate
                .send(ClientConstant.TEST_KAFKA_OBJECT_TOPIC2, response);
        System.out.println("Feign调用doEntity结果："+ new ObjectMapper().writeValueAsString(response));
        System.out.println("kafka发送消息成功,已发送的topic为：" + a.get().getProducerRecord().topic());
        return feign.doEntity(sample);
    }
}
