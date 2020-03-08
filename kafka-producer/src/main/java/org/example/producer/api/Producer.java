package org.example.producer.api;

public interface Producer {
    boolean sendButForgetMessage();
    boolean synSendForgetMessage();
    boolean asynSendForgetMessage();
}
