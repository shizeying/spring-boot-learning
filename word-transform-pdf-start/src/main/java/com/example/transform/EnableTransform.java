package com.example.transform;

import com.example.transform.config.TransformAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({TransformAutoConfiguration.class})
public @interface EnableTransform {
}
