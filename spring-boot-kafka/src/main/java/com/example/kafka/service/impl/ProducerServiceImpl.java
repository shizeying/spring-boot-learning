package com.example.kafka.service.impl;

import static org.hibernate.validator.internal.util.Contracts.*;

import com.example.kafka.config.KafkaTopicProperties;
import com.example.kafka.service.ProducerService;
import com.example.utils.config.JacksonUtil;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class ProducerServiceImpl implements ProducerService {
  @Autowired private KafkaTemplate<String, String> kafkaTemplate;
  @Autowired private KafkaTopicProperties topicProperties;

  @Override
  public void send(Object data) {
    //Contracts.assertTrue( data instanceof BasicEntity,"输出传输错误");
    try {
      Class<? extends Class> aClass = (Class<? extends Class>) data.getClass();
      String className = data.getClass().getName();
      Method getReId = aClass.getMethod("getReId");
      String reId = (String) getReId.invoke(data);
      assertTrue( StringUtils.isNotBlank(reId),"reId不能为空");

      String json = JacksonUtil.bean2Json(data);
      //log.info("reId={}\nelasticsearch to mongo : {} ", reId, json);
      kafkaTemplate.send(topicProperties.getElasticsearchToMongo(), reId + "#" + className, json);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void sendAsync(Object data) {
    //assertTrue( data instanceof BasicEntity,"输出传输错误");
    try {
      Class<? extends Class> aClass = (Class<? extends Class>) data.getClass();
      String className = data.getClass().getName();
      Method getReId = aClass.getMethod("getReId");
      String reId = (String) getReId.invoke(data);
      assertTrue( StringUtils.isNotBlank(reId),"reId不能为空");

      String json = JacksonUtil.bean2Json(data);
      log.info("reId={}\nelasticsearch to mongo : {} ", reId, json);
      ListenableFuture<SendResult<String, String>> future =
          kafkaTemplate.send(
              topicProperties.getElasticsearchToMongo(), reId + "#" + className, json);
      future.addCallback(
          new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
              System.out.println("success");
            }

            @Override
            public void onFailure(Throwable ex) {
              System.out.printf("failure:%s\n" , ex.getMessage());
            }
          });

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
