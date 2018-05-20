package com.mhuang.kafka.common.bean;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @ClassName:  KafkaCustomerBean   
 * @Description:、消费配置bean 
 * @author: mhuang
 * @date:   2017年9月13日 下午8:42:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ConsumerBean extends KafkaBean{

	@JSONField(name = "key.deserializer")
	private String keyDeserializer;	
	
	@JSONField(name = "value.deserializer")
	private String valueDeserializer;
	
	@JSONField(serialize = false,name = "invoke.bean")
	private String invokeBeanName;
	
	@JSONField(serialize = false,name = "invoke.method")
	private String invokeMethodName;
	
	@JSONField(serialize = false,name = "invoke.callback")
	private String invokeCallback;
	
	@JSONField(serialize = false,name = "topics")
	private String topics;
	
	@JSONField(serialize = false,name = "group.id")
	private String groupId;
	
	@JSONField(serialize = false)
	private Integer pull;

	@JSONField(serialize = false,name = "thread.partition.num")
	private Integer threadPartitionNum;
	
	@JSONField(name = "enable.auto.commit")
	private Boolean enableAutoCommit;
	
	@JSONField(name = "auto.commit.interval.ms")
	private Integer autoCommitIntervalMs;
	
	@JSONField(name = "session.timeout.ms")
	private Integer sessionTimeOutMs;
	
	@JSONField(name = "auto.offset.reset")
	private String autoOffsetReset;
}
