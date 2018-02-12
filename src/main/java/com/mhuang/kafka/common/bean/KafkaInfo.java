package com.mhuang.kafka.common.bean;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * 
 * @ClassName:  KafkaInfo   
 * @Description:kafka信息
 * @author: mhuang
 * @date:   2017年9月14日 下午3:59:30
 */
@Data
public class KafkaInfo {

	/**
	 * 对应的通用服务
	 */
	private String servers;
	
	/**
	 * 对应的序列化key
	 */
	private String keySerializer;
	
	/**
	 * 对应的序列化value
	 */
	private String valueSerializer;
	
	/**
	 * 对应的反序列化key
	 */
	private String keyDeserializer;
	
	/**
	 * 对应的反序列化value
	 */
	private String valueDeserializer;

	private Boolean enableProducer;
	private Boolean enableConsumer;
	/**
	 * 生产者信息，对应多个或单个
	 */
	private Map<String, ProducerBean> producerInfo = new HashMap<>();
	
	/**
	 * 消费者信息对应多个或者单个
	 */
	private Map<String, ConsumerBean> consumerInfo = new HashMap<>();
}
