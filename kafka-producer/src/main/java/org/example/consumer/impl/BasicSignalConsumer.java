package org.example.consumer.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.example.consumer.MyConsumer;
import org.example.message.CountryMessage;

import java.time.Duration;
import java.util.*;

public class BasicSignalConsumer implements MyConsumer {
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
        /**
         * 获取主题对应的所有分区
         */
        List<PartitionInfo> serializable = consumer.partitionsFor("serializable");
        if(null == serializable || serializable.isEmpty()) {
            return;
        }
        Collection<TopicPartition> partitions = new ArrayList<>(serializable.size());
        for (PartitionInfo partitionInfo : serializable) {
            partitions.add(new TopicPartition(partitionInfo.topic(),partitionInfo.partition()));
        }
        /**
         * 分配需要消费的主题及分区
         */
        consumer.assign(partitions);

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
            }
        } catch (Exception e) {
            System.out.println("消息读取异常");
            System.out.println(e);
        } finally {
            consumer.close();
        }
    }

    public static void main(String[] args) {
        BasicSignalConsumer basicConsumer = new BasicSignalConsumer();
        basicConsumer.consumerMessage();

    }
}
