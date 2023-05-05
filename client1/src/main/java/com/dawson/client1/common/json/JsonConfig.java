package com.dawson.client1.common.json;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JsonConfig {
    @Value("${app.date-time-pattern:yyyy-MM-dd HH:mm:ss}")
    private String localDateTimePattern;
    @Value("${app.date-pattern:yyyy-MM-dd}")
    private String localDatePattern;
    @Value("${app.time-pattern:HH-mm-ss}")
    private String localTimePattern;
    @Value("${app.time-zone:GMT+8}")
    private String timeZone;

}
