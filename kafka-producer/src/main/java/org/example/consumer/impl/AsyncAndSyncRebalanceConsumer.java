package org.example.consumer.impl;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.example.consumer.MyConsumer;
import org.example.message.CountryMessage;

import java.time.Duration;
import java.util.*;

public class AsyncAndSyncRebalanceConsumer implements MyConsumer {
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
        Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
        consumer.subscribe(Collections.singleton("serializable"), new ConsumerRebalanceListener() {
            /**
             * 失去所有权时触发
             * @param collection
             */
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {
                System.out.println("失去消息分片");
                consumer.commitSync(offsets);
            }

            /**
             * 获取所有权时触发
             * @param collection
             */
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> collection) {
                System.out.println("获取分片");
                for (TopicPartition topicPartition : collection) {
                    System.out.println(topicPartition.topic());
                    System.out.println(topicPartition.partition());
                }
            }
        });

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
                    offsets.put(new TopicPartition(consumerRecord.topic() ,consumerRecord.partition())
                            ,new OffsetAndMetadata(consumerRecord.offset()));
                    consumer.commitAsync(offsets, new OffsetCommitCallback() {
                        @Override
                        public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
                            if(null != e) {
                                e.printStackTrace();
                                return;
                            }
                            Set<Map.Entry<TopicPartition, OffsetAndMetadata>> entries = map.entrySet();
                            StringBuilder stringBuilder = new StringBuilder("提交的消息:");
                            for (Map.Entry<TopicPartition, OffsetAndMetadata> entry : entries) {
                                stringBuilder.append("key:" + entry.getKey());
                                stringBuilder.append("value:" + entry.getValue());
                            }
                            System.out.println(stringBuilder.toString());
                        }
                    });
                }
                /**
                 * 异步提交
                 */

            }
        } catch (Exception e) {
            System.out.println("消息读取异常");
            System.out.println(e);
        } finally {
            try {
                consumer.commitSync(offsets);
            } catch (Exception e) {
                e.printStackTrace();
            }
            consumer.close();
        }
    }

    public static void main(String[] args) {
        AsyncAndSyncRebalanceConsumer basicConsumer = new AsyncAndSyncRebalanceConsumer();
        basicConsumer.consumerMessage();

    }
}
