package com.znv.framework.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

/**
 *
 * Created by MaHuiming on 2018/11/1.
 */
@Slf4j
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String data) {
        log.info("sending topic='{}' data='{}'", topic, data);
        kafkaTemplate.send(topic, data);
    }
}
