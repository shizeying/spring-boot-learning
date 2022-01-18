package com.example.utils;

import java.beans.PropertyDescriptor;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

public class CustomerBeanUtils {
    public static <T, R> Function<T, R> convert(Class<R> clazz) {
        return source -> {
            try {
                R target = clazz.newInstance();
                BeanUtils.copyProperties(source, target);
                return target;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        };

    }

    public static <T, R> Function<T, R> updateConvert(Class<R> clazz) {
        return source -> {
            try {
                R target = clazz.newInstance();
                BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
                return target;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        };
    }

    private static String[] getNullPropertyNames(Object object) {
        final BeanWrapperImpl beanWrapper = new BeanWrapperImpl(object);
       return Stream.of(beanWrapper.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(name -> Objects.isNull(beanWrapper.getPropertyValue(name)))
                .toArray(String[]::new);


    }
}
