package org.example.consumer.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.example.consumer.ConsumerMsg;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerMsg implements ConsumerMsg {
    KafkaConsumer<String,String> consumer;
    {
        Properties properties = new Properties();
        /**
         * kafka broker地址
         */
        properties.put("bootstrap.servers","47.96.121.230:9092");
        /**
         * 消费群组id
         */
        properties.put("group.id","stream");
        /**
         * 消息偏移量提交方式
         */
        properties.put("enable.auto.commit","false");
        /**
         * key 反序列化方式
         */
        properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        /**
         * value 反序列化方式
         */
        properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<String, String>(properties);
    }
    @Override
    public void consumeMsg(String topicName) {
        this.consumer.subscribe(Collections.singleton(topicName));
        try {
            while(true) {
                ConsumerRecords<String, String> consumerRecords = this.consumer.poll(Duration.ofSeconds(10));
                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    System.out.println("key:" +consumerRecord.key() + ";value:" + consumerRecord.value());
                }
                this.consumer.commitAsync();
            }
        } catch (Exception e) {
            try {
                this.consumer.commitSync();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            this.consumer.close();
        }
    }
}
