package com.example.jwt.config;

import com.example.jwt.constant.RestConstants;
import com.example.swagger.config.SwaggerConfiguration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Component
public class JwtSwaggerConfiguration extends SwaggerConfiguration {
  private ApiKey apiKey() {
    return new ApiKey("JWT", RestConstants.AUTH_HEADER, "header");
  }
  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth()).build();
  }
  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
  }

  @Primary
  @Bean
  @Override
  public Docket createRestApi() {
    Docket restApi = super.createRestApi();
    restApi.securityContexts(Collections.singletonList(securityContext()));
    restApi.securitySchemes(Collections.singletonList(apiKey()))
        .select()
        .apis(RequestHandlerSelectors.any())
    ;
    return  restApi;
  }
}
