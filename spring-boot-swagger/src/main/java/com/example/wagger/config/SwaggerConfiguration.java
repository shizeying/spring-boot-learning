package com.example.wagger.config;

import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@Configuration
@EnableSwagger2
// TODO 通过dev方式指定
// @Profile({"dev","test"})
@ConditionalOnProperty(
    prefix = "swagger",
    value = {"enable"},
    havingValue = "true")
public class SwaggerConfiguration {
  
  
  
  
  MavenXpp3Reader reader = new MavenXpp3Reader();

  Model model;

  {
    try {
      model = reader.read(new FileReader("pom.xml"));
    } catch (IOException | XmlPullParserException e) {
      e.printStackTrace();
    }
  }

  @Bean
  public Docket createRestApi() {

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.example.wagger"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .termsOfServiceUrl("http://localhost:8080")
        .title(model.getName())
        .description(model.getDescription())
        .version(model.getVersion())
        .build();
  }
}
