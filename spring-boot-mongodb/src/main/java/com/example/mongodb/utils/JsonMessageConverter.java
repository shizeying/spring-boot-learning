package com.example.mongodb.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JsonMessageConverter {

  @Bean
  public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
    ObjectMapper objectMapper = builder.createXmlMapper(false).build();

    // 将json属性的空值null转化为空字符串""
    objectMapper
        .getSerializerProvider()
        .setNullValueSerializer(
            new JsonSerializer<Object>() {
              @Override
              public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers)
                  throws IOException {
                gen.writeString("");
              }
            });

    SimpleModule module = new SimpleModule();
    module.addSerializer(ObjectId.class, new ObjectIdSerializer());
    objectMapper.registerModule(module);
    return objectMapper;
  }
}
