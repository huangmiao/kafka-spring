package com.mhuang.kafka.common.notify;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.mhuang.kafka.common.producer.event.ProducerEvent;

/**
 * 
 * @ClassName:  KafkaNotifyServer   
 * @Description:kafka通知服务
 * @author: mhuang
 * @date:   2017年9月15日 下午4:46:48
 */
@Component
public class KafkaNotify {

	@Autowired
    ApplicationContext context;
	
	/**
	 * 
	 * @Title: producerPublish   
	 * @Description: 通知加载所有生产者的配置
	 * 	注：若存在相应的配置则修改。没有则添加。
	 * @param map
	 * @return void
	 */
	public void producerPublish(Map<String, Object> map){
		context.publishEvent(new ProducerEvent(this, map));
	}
	
	/**
	 * 
	 * @Title: producerPublish   
	 * @Description: 通知加载所有生产者的配置（单个配置）
	 * 	注：若存在相应的配置则修改。没有则添加。
	 * 	比如bootstrap.servers 代表key。value就是你想要的结果
	 * @param key
	 * 			更改的配置名。
	 * @param value
	 * 			更改的值
	 * @return void
	 */
	public void producerPublish(String key,Object value){
		context.publishEvent(new ProducerEvent(this, key,value));
	}
}
