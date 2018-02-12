package com.mhuang.kafka.common.producer.send;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import com.alibaba.fastjson.JSONObject;
import com.mhuang.kafka.common.bean.KafkaInfo;
import com.mhuang.kafka.common.bean.ProducerBean;
import com.mhuang.kafka.common.producer.event.ProducerEvent;
import com.mhuang.kafka.common.utils.BeanIgnoreNullProperty;

import lombok.Setter;

/**
 * @ClassName:  KafkaProducer   
 * @Description:消息发送  
 * @author: mhuang
 * @date:   2017年9月12日 下午4:38:37
 */
public class KafkaJProducer<K,V> implements IKafkaJProducer<K,V>, ApplicationListener<ProducerEvent>{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private KafkaInfo kafkaInfo;
	
	/**
	 * kafka producer
	 */
	@Setter
	private Producer<K, V> producer;
	
	/**
	 * kafka producer properties
	 */
	private Properties prop;
	
	@Setter
	private String name;
	
	@Override
	public Future<RecordMetadata> send(String topic, K key, V value){
		logger.debug("正在发送kafka数据-->topic:{},key:{},value:{}",topic,key,value);
		return producer.send(new ProducerRecord<K, V>(topic, key, value));
	}
	
	@Override
	public Future<RecordMetadata> send(String topic,K key,V value,Callback callBack){
		logger.debug("正在发送kafka数据-->topic:{},key:{},value:{}",topic,key,value);
		return producer.send(new ProducerRecord<K, V>(topic, key, value),callBack);
	}
	
	@Override
	public Future<RecordMetadata> send(ProducerRecord<K,V> record){
		return producer.send(record);
	}
	
	@Override
	public Future<RecordMetadata> send(ProducerRecord<K,V> record,Callback callBack){
		return producer.send(record,callBack);
	}

	public void setProp(Properties prop) {
		this.prop = prop;
		refreshProp();
	}
	private void refreshProp(){
		producer = new KafkaProducer<K,V>(prop);
	}

	@Override
	public void onApplicationEvent(ProducerEvent event) {
		logger.info("reloading properties");
		setProperty(event.getMap());
		logger.info("reload properties success");
	}

	@Override
	public void setProperty(String key, Object value) {
		this.prop.put(key, value);
		refreshProp();
	}

	@Override
	public void setProperty(Map<String, Object> map) {
		this.prop.putAll(map);
		setInfo(map);
		refreshProp();
	}
	
	private void setInfo(Map<String, Object> map){
		ProducerBean newBean = new JSONObject(map).toJavaObject(ProducerBean.class);
		BeanUtils.copyProperties(newBean, 
				kafkaInfo.getProducerInfo().get(name),
				BeanIgnoreNullProperty.getNullPropertyNames(newBean));
	}
}

