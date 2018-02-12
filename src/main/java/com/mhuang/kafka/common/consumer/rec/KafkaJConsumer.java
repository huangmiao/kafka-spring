package com.mhuang.kafka.common.consumer.rec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
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
		for(PartitionInfo partition : partitionList){
			cloneProps.put("group.id", topic + "-group-" + partition.partition());
			initPartition(cloneProps,partition);
		}
			
//		partitionList.parallelStream().forEach(partition->{
//			initPartition(cloneProps,partition);
//		});
	}
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
