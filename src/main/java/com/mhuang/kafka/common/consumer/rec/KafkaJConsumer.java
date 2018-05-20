package com.mhuang.kafka.common.consumer.rec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mhuang.kafka.common.bean.ConsumerBean;
import com.mhuang.kafka.common.consumer.thread.KafkaJConsumerThread;
import com.mhuang.kafka.common.utils.SpringContextHolder;

import lombok.Setter;

/**
 * 
 * @ClassName:  KafkaJConsumer   
 * @Description:消息接收者
 * @author: mhuang
 * @date:   2017年9月18日 下午5:01:50
 */
public class KafkaJConsumer {

	@Setter
	private String name;

	@Setter
	private ConsumerBean consumerBean;
	
	@Autowired
	@Qualifier("kafkaJPoolExecutor")
	private ThreadPoolTaskExecutor executor;
	/**
	 * 
	 * @Title: init   
	 * @Description: init consumer
	 * @return void
	 */
	public void init(){
		Map<String, Object> map = ((JSONObject)JSON.toJSON(consumerBean)).getInnerMap();
		String[] topics = StringUtils.split(consumerBean.getTopics(), ",");
		if(topics == null){
			if(StringUtils.isEmpty(consumerBean.getTopics())){
				return;
			}
			initTopic(map,consumerBean.getTopics());
		}else{
			for(String topic:topics){
				initTopic(map,topic);
			}
		}
	}
	
	private void initTopic(Map<String, Object> props,String topic){
		Map<String, Object> cloneProps = new HashMap<>(props);
		KafkaConsumer<Object, Object> consumer = new KafkaConsumer<>(props);
		List<PartitionInfo> partitionList = consumer.partitionsFor(topic);
		consumer.close();
		Integer threadPartionNum = consumerBean.getThreadPartitionNum();
		List<TopicPartition>  operaParttionList = new ArrayList<>();
		StringBuilder  sb = new StringBuilder();
		for(int i = 0,j = partitionList.size(); i<j; i++){
			int partition = partitionList.get(i).partition();
			if(j - 1 == i || (threadPartionNum -1) % i == 0){//解决除数为0的情况下
				sb.append("|").append(partition);
				operaParttionList.add(new TopicPartition(topic, partition));
				cloneProps.put("group.id", sb.insert(0, "-group-").insert(0, topic).toString());
				initPartition(cloneProps,operaParttionList);
				operaParttionList = new ArrayList<>(); 
				sb = new StringBuilder();
			}else{
				sb.append("|").append(partition);
			}
		}
		/*
		@Deprecated
		for(PartitionInfo partition : partitionList){
			cloneProps.put("group.id", topic + "-group-" + partition.partition());
			initPartition(cloneProps,partition);
		}
		*/
	}
	
	private void initPartition(Map<String, Object> props,List<TopicPartition> partitions){
		Map<String, Object> params = new HashMap<>(5);
		params.put("consumerMap", props);
		params.put("consumerBean", consumerBean);
		params.put("partition", partitions);
		KafkaJConsumerThread kafkaThread = SpringContextHolder.registerBean(
				String.valueOf(props.get("group.id")), 
			KafkaJConsumerThread.class, params
		);
		executor.submit(kafkaThread);
	}
	
	/**
	 * 
	 * @Title: initPartition   
	 * @Description: 旧方法
	 *   0.0.3.3之前使用
	 * @param props
	 * @param partition
	 * @return void
	 */
	@Deprecated
	private void initPartition(Map<String, Object> props,PartitionInfo partition){
		Map<String, Object> params = new HashMap<>(5);
		params.put("consumerMap", props);
		params.put("consumerBean", consumerBean);
		params.put("partition", partition);
		KafkaJConsumerThread kafkaThread = SpringContextHolder.registerBean(
			partition.topic() + "-group-" + partition.partition(), 
			KafkaJConsumerThread.class, params
		);
		executor.submit(kafkaThread);
	}
}
