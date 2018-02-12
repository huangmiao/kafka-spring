package com.mhuang.kafka.common.bean;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @ClassName:  KafkaProducerBean   
 * @Description: kafka生产者配置
 * @author: mhuang
 * @date:   2017年9月12日 下午4:24:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProducerBean  extends KafkaBean{

	@JSONField(name = "key.serializer")
	private String keySerializer;	
	
	@JSONField(name = "value.serializer")
	private String valueSerializer;
	
	@JSONField(name = "acks")
	private String acks;
	
	@JSONField(name=  "retries")
	private String retries;
	
	@JSONField(name = "batch.size")
	private Integer batchSize;
	
	@JSONField(name = "linger.ms")
	private Long lingerMs;
	
	
	@JSONField(name = "buffer.memory")
	private Long bufferMemory;
	
	@JSONField(name = "partitioner.class")
	private String partitionerClass;
}
