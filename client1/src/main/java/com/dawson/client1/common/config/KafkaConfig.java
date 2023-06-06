package com.dawson.client1.common.config;

import com.dawson.client1.common.response.R;
import com.dawson.client1.common.response.RRExceptionEnum;
import com.dawson.client1.dto.entity.CustomEntitySample;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers:120.27.193.111:9092}")
    private String bootStrapServer;
    @Bean
    public KafkaTemplate<String, String> stringKafkaTemplate(ProducerFactory<String, String> pf) {
        return new KafkaTemplate<String, String>(pf,
                Collections.singletonMap(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class));
    }
    @Bean
    public KafkaTemplate<String, CustomEntitySample> customEntitySampleKafkaTemplate(
            ProducerFactory<String, CustomEntitySample> pf) {
        return new KafkaTemplate<String, CustomEntitySample>(pf,stringObjectTemplateProps());
    }
    @Bean
    public KafkaTemplate<String, R> rKafkaTemplate(
            ProducerFactory<String, R> pf) {
        return new KafkaTemplate<String, R>(pf,stringObjectTemplateProps());
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
            stringKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }

    private Map<String, Object> stringObjectTemplateProps(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, CustomEntitySample>>
            customEntitySampleKafKaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, CustomEntitySample> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        JsonDeserializer<CustomEntitySample> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("com.dawson.client1.dto.entity");
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(customEntitySampleConsumerConfigs(),
                new StringDeserializer(), jsonDeserializer));
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, R>>
        rKafKaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, R> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(customEntitySampleConsumerConfigs()));
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);

        return factory;
    }


    @Bean
    public ConsumerFactory<String, String> stringConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(stringConsumerConfigs());
    }

    @Bean
    public Map<String, Object> stringConsumerConfigs(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        return props;

    }

    @Bean
    public Map<String, Object> customEntitySampleConsumerConfigs(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ErrorHandlingDeserializer.VALUE_FUNCTION, ConsumerErrHandler.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, ConsumerErrHandler.class);
        return props;
    }
}
