package com.mhuang.kafka.common.consumer.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.mhuang.kafka.common.bean.ConsumerBean;
import com.mhuang.kafka.common.bean.KafkaCallbackBean;
import com.mhuang.kafka.common.bean.KafkaMsg;
import com.mhuang.kafka.common.utils.SpringExecute;

import lombok.Setter;

/**
 * 
 * @ClassName:  KafkaJConsumerPool   
 * @Description:消费线程池
 * @author: mhuang
 * @date:   2017年9月18日 上午11:35:54
 */
public class KafkaJConsumerThread implements Runnable{

//	@Setter
//	private PartitionInfo partition;
	@Setter
	private List<TopicPartition> partition;
	
	@Setter
	private Map<String, Object> consumerMap;

	@Setter
	private ConsumerBean consumerBean;
	
	@Setter
	private AtomicBoolean running = new AtomicBoolean(true);
	
	@Setter
	private KafkaConsumer<Object, Object>  consumer;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void run(){
		//use consumer status for start
		logger.info("starting consumer");
		Boolean isCallBackMethod = !StringUtils.isEmpty(consumerBean.getInvokeCallback());
		logger.info("start conumser properties:{}",consumerMap);
		consumer = new KafkaConsumer<>(consumerMap);
//		consumer.assign(Arrays.asList(new TopicPartition(partition.topic(), partition.partition())));
		consumer.assign(partition);
		
		while(running.get()){
			ConsumerRecords<Object, Object> records = consumer.poll(consumerBean.getPull());
			records.forEach(record->{
				logger.debug("kafka反馈给系统的数据是:{}",records);
				KafkaMsg kafkaMsg = new KafkaMsg(record.topic(),record.offset(),record.value());
				try {
					SpringExecute.getMethodToValue(consumerBean.getInvokeBeanName(), consumerBean.getInvokeMethodName(), kafkaMsg);
				} catch (Exception e) {
				    logger.error("消费数据异常",e);
				}
			});
			
			if(isCallBackMethod && !records.isEmpty()){
				consumer.commitAsync((offsets,ex)->{
					List<KafkaCallbackBean> listBean = new ArrayList<>(offsets.size());
					offsets.forEach((parition,metadata)->{
						KafkaCallbackBean callBackBean = new KafkaCallbackBean(parition.topic(), metadata.offset(), ex);
						listBean.add(callBackBean);
					});
					try {
						SpringExecute.getMethodToValue(consumerBean.getInvokeBeanName(), consumerBean.getInvokeCallback(), listBean);
					} catch (Exception e) {
					    logger.error("消费缺人数据异常",e);
					}
				});
			}else{
				consumer.commitAsync();
			}
		}
		logger.info("consumer close");
	}
}
