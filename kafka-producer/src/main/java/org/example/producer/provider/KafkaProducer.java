package org.example.producer.provider;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.protocol.types.Field;
import org.example.producer.api.Producer;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaProducer implements Producer {
    private org.apache.kafka.clients.producer.KafkaProducer<String,String> producer;

    public org.apache.kafka.clients.producer.KafkaProducer<String, String> getProducer() {
        return producer;
    }

    {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","47.96.121.230:9092");
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(properties);
    }
    public static void main(String[] args) throws IOException {
        KafkaProducer kafkaProducer = new KafkaProducer();
        System.out.println(kafkaProducer.getProducer());
        //kafkaProducer.sendMessage();
        boolean b = kafkaProducer.sendButForgetMessage();
        //boolean b = kafkaProducer.synSendForgetMessage();
        String result = "异步发送完成";
        System.out.println(result);
        System.in.read();
    }

    @Override
    public boolean sendButForgetMessage() {
        ProducerRecord<String, String> stringStringProducerRecord = new ProducerRecord<>("CustomerCountry", "precision productes","china");
        try {
            System.out.println("开始发送");
            Future<RecordMetadata> send = this.producer.send(stringStringProducerRecord);
           // RecordMetadata recordMetadata = send.get();
            //System.out.println(recordMetadata);
            System.out.println("发送完成");
            return true;
        } catch (Exception exp) {
            System.out.println("error");
            exp.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean synSendForgetMessage() {
        ProducerRecord<String, String> stringStringProducerRecord = new ProducerRecord<>("test", "japan");
        try {
            System.out.println("开始发送");
            this.producer.send(stringStringProducerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(null != e) {
                        e.printStackTrace();
                        return;
                    }
                    System.out.println(recordMetadata);
                }
            });
            System.out.println("发送完成");
            return true;
        } catch (Exception exp) {
            System.out.println("error");
            exp.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean asynSendForgetMessage() {
        return false;
    }
}
