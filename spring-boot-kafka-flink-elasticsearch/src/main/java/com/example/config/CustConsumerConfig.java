package com.example.config;

import org.apache.kafka.clients.consumer.*;

import java.util.*;

public class CustConsumerConfig extends ConsumerConfig {
	
	public static final String ZOOKEEPER_CONNECT = "zookeeper.connect";
	
	public CustConsumerConfig(final Properties props) {
		super(props);
	}
}
