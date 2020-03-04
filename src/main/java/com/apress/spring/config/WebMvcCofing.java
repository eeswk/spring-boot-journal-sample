package com.apress.spring.config;

import com.apress.spring.config.interceptor.ConcurrentTransactionCounterInterceptor;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcCofing implements WebMvcConfigurer {

    @Autowired
    private MeterRegistry meterRegistry;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ConcurrentTransactionCounterInterceptor(meterRegistry))
                .addPathPatterns("/*");
    }
}
