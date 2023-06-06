package com.dawson.client1.common.config;

import com.dawson.client1.common.response.R;
import com.dawson.client1.common.response.RRException;
import com.dawson.client1.common.response.RRExceptionEnum;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.serializer.FailedDeserializationInfo;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import sun.misc.REException;

import java.util.function.Function;

@Component
public class ConsumerErrHandler implements KafkaListenerErrorHandler {

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        return R.error(RRExceptionEnum.JSON_ERROR);
    }
}
