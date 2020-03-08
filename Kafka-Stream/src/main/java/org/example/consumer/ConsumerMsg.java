package org.example.consumer;

import java.io.ObjectInputStream;

public interface ConsumerMsg {
    void consumeMsg(String topicName);
}
