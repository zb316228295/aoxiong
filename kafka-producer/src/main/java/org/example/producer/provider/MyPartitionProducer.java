package org.example.producer.provider;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.example.message.CountryMessage;
import org.example.message.PartitionMessage;
import org.example.producer.api.Producer;

import java.util.Properties;
import java.util.concurrent.Future;

public class MyPartitionProducer implements Producer {
    KafkaProducer<String, PartitionMessage> producer;
    {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","47.96.121.230:9092");
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.example.serializer.ValueSerializer");
        properties.put("partitioner.class","org.example.partition.BananaPartition");
        producer = new org.apache.kafka.clients.producer.KafkaProducer<String, PartitionMessage>(properties);

    }
    @Override
    public boolean sendButForgetMessage() {
        return false;
    }

    @Override
    public boolean synSendForgetMessage() {
        return false;
    }

    @Override
    public boolean asynSendForgetMessage() {
        ProducerRecord<String, PartitionMessage> producerRecord = new ProducerRecord<>("mypatition", "banana", new PartitionMessage("", 1));
        Future<RecordMetadata> send = this.producer.send(producerRecord);
        try {
            RecordMetadata recordMetadata = send.get();
            System.out.println(recordMetadata);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        MyPartitionProducer producer = new MyPartitionProducer();
        producer.asynSendForgetMessage();
    }
}
