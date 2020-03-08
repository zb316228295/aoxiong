package org.example.consumer.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.example.consumer.MyConsumer;
import org.example.message.CountryMessage;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class AsyncConsumer implements MyConsumer {
    KafkaConsumer<String, CountryMessage> consumer;
    {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","47.96.121.230:9092");
        /**
         * 设置消息消费组id
         */
        properties.put("group.id","serializable");
        properties.put("enable.auto.commit","false");
        properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.example.deserializer.ObjectDeserializer");
        consumer = new KafkaConsumer<String, CountryMessage>(properties);
    }
    @Override
    public void consumerMessage() {
        consumer.subscribe(Collections.singleton("serializable"));
        try {
            while(true) {
                System.out.println("消息轮询");
                ConsumerRecords<String, CountryMessage> consumerRecords = consumer.poll(Duration.ofSeconds(2));
                for (ConsumerRecord<String, CountryMessage> consumerRecord : consumerRecords) {
                    System.out.println("主题是：" + consumerRecord.topic());
                    System.out.println("偏移量是：" + consumerRecord.offset());
                    System.out.println("主题的键是：" + consumerRecord.key());
                    System.out.println("主题的值是" + consumerRecord.value());
                    System.out.println("主题的分区是：" + consumerRecord.partition());
                }
                /**
                 * 异步提交
                 */
                consumer.commitAsync();
            }
        } catch (Exception e) {
            System.out.println("消息读取异常");
            System.out.println(e);
        } finally {
            consumer.close();
        }
    }

    public static void main(String[] args) {
        AsyncConsumer basicConsumer = new AsyncConsumer();
        basicConsumer.consumerMessage();

    }
}
