package com.datasoft.bkash.ea.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Callable;

@Slf4j
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfig implements AsyncConfigurer {


//    @Override
//    @Bean(name = "taskExecutor")
//    public AsyncTaskExecutor getAsyncExecutor() {
//        log.debug("Creating Async Task Executor");
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(?);
//        executor.setMaxPoolSize(?);
//        executor.setQueueCapacity(?);
//        executor.setThreadNamePrefix(?);
//        return executor;
//    }

    @Value("${async-timeout}")
    private Long asyncTimeout;

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

//    @Bean
//    public WebMvcConfigurer webMvcConfigurerAdapter(AsyncTaskExecutor taskExecutor, CallableProcessingInterceptor callableProcessingInterceptor) {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//                configurer.setDefaultTimeout(asyncTimeout)
//                    .setTaskExecutor(taskExecutor);
//                configurer.registerCallableInterceptors(callableProcessingInterceptor);
//                super.configureAsyncSupport(configurer);
//            }
//        };
//    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(@Qualifier("taskExecutor")  AsyncTaskExecutor taskExecutor, CallableProcessingInterceptor callableInterceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
                configurer.setDefaultTimeout(asyncTimeout)
                        .setTaskExecutor(taskExecutor)
                        .registerCallableInterceptors(callableInterceptor);
            }
        };
    }

    @Bean
    @Primary
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(100);
        executor.initialize();
        return executor;
    }

    @Bean
    public CallableProcessingInterceptor callableProcessingInterceptor() {
        return new TimeoutCallableProcessingInterceptor() {
            @Override
            public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task) throws Exception {
                log.error("timeout!");
                return super.handleTimeout(request, task);
            }
        };
    }
}