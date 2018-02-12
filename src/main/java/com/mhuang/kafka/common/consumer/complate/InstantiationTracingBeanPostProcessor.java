package com.mhuang.kafka.common.consumer.complate;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.mhuang.kafka.common.bean.KafkaInfo;
import com.mhuang.kafka.common.consumer.rec.KafkaJConsumer;
import com.mhuang.kafka.common.utils.SpringContextHolder;

/**
 * 
 * @ClassName:  InstantiationTracingBeanPostProcessor   
 * @Description:初始化创建类
 * @author: mhuang
 * @date:   2018年1月26日 下午3:16:36
 */
@Component
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent>{
	@Autowired
	private KafkaInfo kafkaInfo;
	
	private AtomicBoolean loadEnableConsumer = new AtomicBoolean(true);
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(loadEnableConsumer.getAndSet(false)){
			if(kafkaInfo.getEnableConsumer()){
				logger.info("initing consumer");
				kafkaInfo.getConsumerInfo().forEach((name,consumer)->{
					KafkaJConsumer kafkaJConsumer = SpringContextHolder.getBean(name,KafkaJConsumer.class);
					kafkaJConsumer.setConsumerBean(consumer);
					kafkaJConsumer.init();
				});
				logger.info("init consumer success");
			}
		}
	}
}
