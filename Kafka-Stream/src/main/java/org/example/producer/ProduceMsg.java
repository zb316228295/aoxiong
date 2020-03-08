package org.example.producer;

public interface ProduceMsg {
    void produceMsg(String topicName,String key,String value);
}
