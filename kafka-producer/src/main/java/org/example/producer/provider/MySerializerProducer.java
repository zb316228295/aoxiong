package org.example.producer.provider;

import lombok.val;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.message.CountryMessage;
import org.example.producer.api.Producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MySerializerProducer implements Producer {
    private KafkaProducer<String, CountryMessage> producer;


    {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","47.96.121.230:9092");
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.example.serializer.ValueSerializer");
        producer = new org.apache.kafka.clients.producer.KafkaProducer<String, CountryMessage>(properties);
    }
    @Override
    public boolean sendButForgetMessage() {
        return false;
    }

    @Override
    public boolean synSendForgetMessage() {
        ProducerRecord producerRecord = new ProducerRecord<String, CountryMessage>("serializable",new CountryMessage("china","cq"));
        System.out.println("开始发送");
        Future send = this.producer.send(producerRecord);
        try {
            Object o = send.get();
            System.out.println("发送完成");
            System.out.println(o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean asynSendForgetMessage() {
        return false;
    }

    public static void main(String[] args) {
        MySerializerProducer mySerializerProducer = new MySerializerProducer();
        mySerializerProducer.synSendForgetMessage();

    }
}
