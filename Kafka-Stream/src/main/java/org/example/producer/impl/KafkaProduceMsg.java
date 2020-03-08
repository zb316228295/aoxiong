package org.example.producer.impl;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.example.producer.ProduceMsg;

import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaProduceMsg implements ProduceMsg {
    KafkaProducer<String, String> producer;
    {
        Properties properties = new Properties();
        /*
        kafka broker地址
         */
        properties.put("bootstrap.servers","47.96.121.230:9092");


        /**
         * key 序列化方式
         */
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        /**
         * value 序列化方式
         */
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(properties);

    }
    @Override
    public void produceMsg(String topicName, String key, String value) {
        Future<RecordMetadata> future = this.producer.send(new ProducerRecord<>(topicName, key, value));
        try {
            RecordMetadata recordMetadata = future.get();
            System.out.println(recordMetadata);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        KafkaProduceMsg kafkaProduceMsg = new KafkaProduceMsg();
        kafkaProduceMsg.produceMsg("country-topic","cq","a1");
        kafkaProduceMsg.produceMsg("country-topic","wh","a4");
        kafkaProduceMsg.produceMsg("country-topic","cq","a2");
        kafkaProduceMsg.produceMsg("country-topic","cq","a3");

        kafkaProduceMsg.produceMsg("country-topic","wh","a5");
        //kafkaProduceMsg.produceMsg("country-topic","wh","a6");
    }
}
