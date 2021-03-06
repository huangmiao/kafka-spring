package com.mhuang.kafka.common;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 
 * @ClassName:  KafkaConfig   
 * @Description:kafka启动类
 * @author: mhuang
 * @date:   2018年1月26日 下午3:15:58
 */
@Configuration
@ComponentScan
public class KafkaConfig {
	
	@Autowired
	private Environment env;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Bean(name = "kafkaJPoolExecutor")
    public Executor kafkaJPoolExecutor() { 
		logger.info("loading kafkaJPoolExecutor properties");
		ThreadPoolTaskExecutor executor = null;
		if(env.getProperty("kafka.consumer.enable", Boolean.class,Boolean.FALSE)){
			executor = new ThreadPoolTaskExecutor();  
	        executor.setCorePoolSize(env.getProperty("kafka.pool.core", Integer.class, 20));  
	        executor.setMaxPoolSize(env.getProperty("kafka.pool.max", Integer.class, 1000));  
	        executor.setQueueCapacity(env.getProperty("kafka.pool.queue", Integer.class, 200));  
	        executor.setBeanName(env.getProperty("kafka.pool.name", String.class, "kafkaJPoolExecutor"));
	        // rejection-policy：当pool已经达到max size的时候，如何处理新任务  
	        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行  
	        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());  
	        executor.initialize();  
		}
		logger.info("kafkaJPoolExecutor properties success");
        return executor; 
    }  
}
