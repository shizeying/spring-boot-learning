package com.example.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

public interface ConsumerService {
	/**
	 * 接收来自elasticsearch的topic；消费该topic
	 * @param record 记录
	 * @param acknowledgment
	 *
	 *
	 */
	public void listen(ConsumerRecord<String, String> record, Acknowledgment acknowledgment);
}
