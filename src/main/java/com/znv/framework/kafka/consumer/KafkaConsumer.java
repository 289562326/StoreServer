package com.znv.framework.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * 自动提交的方式，enable.auto.commit设置为true，ack手动提交的方式不能用
 * Created by MaHuiming on 2018/11/2.
 */
@Slf4j
//@Component
public class KafkaConsumer {

    /**
     * 自动提交的方式就不能设置消息有ack下面的那种手动方式
     * @param record
     */
    @KafkaListener(topics = {"spring_boot_test"}, containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<?, ?> record) {
        log.info("kafka的key: " + record.key());
        log.info("kafka的value: " + record.value().toString());
    }


//    //"${topic.springboottest:spring_boot_test}"
//    @KafkaListener(topics = {"spring_boot_test"})
//    public void listen(String record, Acknowledgment ack){
//        ack.acknowledge();
//        log.info("facebase----------------------:{}",record);
//    }

}
