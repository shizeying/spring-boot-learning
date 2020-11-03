package com.example.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import java.net.InetAddress;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@EnableKnife4j
@EnableSwagger2
@ComponentScan("com.example")
@SpringBootApplication
// @ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class SpringBootSwaggerApplication
    implements ApplicationListener<WebServerInitializedEvent> {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootSwaggerApplication.class, args);
  }

  @SneakyThrows
  @Override
  public void onApplicationEvent(WebServerInitializedEvent event) {
    WebServer server = event.getWebServer();
    WebServerApplicationContext context = event.getApplicationContext();
    Environment env = context.getEnvironment();
    String ip = InetAddress.getLocalHost().getHostAddress();
    int port = server.getPort();
    String contextPath = env.getProperty("server.servlet.context-path");
    if (contextPath == null) {
      contextPath = "";
    }
    log.info(
        "\n---------------------------------------------------------\n"
            + "\tApplication is running! Access address:\n"
            + "\tLocal:\t\thttp://localhost:{}"
            + "\n\tExternal:\thttp://{}:{}{}"
            + "\n---------------------------------------------------------\n",
        port,
        ip,
        port,
        contextPath);
    log.info("External:http://{}:{}{}", ip, port, "/doc.html", contextPath);
  }
}
