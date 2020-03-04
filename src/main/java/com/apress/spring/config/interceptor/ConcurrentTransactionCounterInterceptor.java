package com.apress.spring.config.interceptor;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConcurrentTransactionCounterInterceptor extends HandlerInterceptorAdapter {
    private static Logger log = LoggerFactory.getLogger(ConcurrentTransactionCounterInterceptor.class);

    private final Counter counter;

    public ConcurrentTransactionCounterInterceptor(MeterRegistry meterRegistry) {
        this.counter = meterRegistry.counter("transaction.current.count");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("ConcurrentTransactionCounterInterceptor > preHandle");
        counter.increment();
        log.info("counter prehandle : " + counter);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //counter.increment(-1d);
        log.info("counter after : " + counter);
    }
}
