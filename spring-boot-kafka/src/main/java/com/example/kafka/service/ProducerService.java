package com.example.kafka.service;

public interface ProducerService {
  public void send(Object data);
  public void sendAsync(Object data);
}
