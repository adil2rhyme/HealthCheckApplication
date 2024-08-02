package com.example.HealthCheckApplication;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class CustomCacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return target.getClass().getName() + "." + method.getName() + "(" + Arrays.toString(params) + ")";
    }
}

