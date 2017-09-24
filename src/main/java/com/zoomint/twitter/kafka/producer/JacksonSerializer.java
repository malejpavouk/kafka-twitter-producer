package com.zoomint.twitter.kafka.producer;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * Custom serializer used for Jackson object mapper configuration
 */
public class JacksonSerializer extends JsonSerializer {
    public JacksonSerializer() {
        super();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
