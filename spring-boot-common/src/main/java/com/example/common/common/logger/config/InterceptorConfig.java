package com.example.common.common.logger.config;

import com.example.common.common.logger.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
    }

    /**
     * swagger配置
     *
     * @param registry 注册表
     */
    //@Override
    //public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //    registry.addResourceHandler("/**").addResourceLocations(
    //            "classpath:/static/");
    //    registry.addResourceHandler("swagger-ui.html").addResourceLocations(
    //            "classpath:/META-INF/resources/");
    //    registry.addResourceHandler("/webjars/**").addResourceLocations(
    //            "classpath:/META-INF/resources/webjars/");
    //    super.addResourceHandlers(registry);
    //}
}