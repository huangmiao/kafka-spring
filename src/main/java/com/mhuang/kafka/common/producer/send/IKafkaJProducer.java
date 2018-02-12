package com.mhuang.kafka.common.producer.send;

import java.util.Map;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * 
 * @ClassName:  IKafkaJProducer   
 * @Description:生产者接口
 * @author: mhuang
 * @date:   2017年9月15日 上午11:04:27   
 * @param <K>
 * @param <V>
 */
public interface IKafkaJProducer<K,V> {

	/**
	 * 发送
	 * @Title: send   
	 * @param topic
	 * 			主题
	 * @param key
	 * 			键
	 * @param value
	 * 			值
	 * @return
	 * @return Future<RecordMetadata>
	 * 		可get同步等待获取进行下一步。也可忽略。
	 */
	public Future<RecordMetadata> send(String topic, K key, V value);
	
	/**
	 * 发送
	 * @Title: send   
	 * @param topic 主题
	 * @param key 键
	 * @param value 值
	 * @param callBack 不同步的时候回调
	 * @return Future<RecordMetadata> 可get同步等待获取进行下一步。也可忽略。
	 */
	public Future<RecordMetadata> send(String topic,K key,V value,Callback callBack);
	
	
	/**
	 * 发送
	 * @Title: send   
	 * @param record 发送的数据
	 * @return
	 * @return Future<RecordMetadata> 可get同步等待获取进行下一步。也可忽略。
	 */
	public Future<RecordMetadata> send(ProducerRecord<K,V> record);
	
	/**
	 * 发送
	 * @Title: send   
	 * @param record 发送的数据
	 * @param callBack 回调
	 * @return Future<RecordMetadata> 可get同步等待获取进行下一步。也可忽略。
	 */
	public Future<RecordMetadata> send(ProducerRecord<K,V> record,Callback callBack);
	
	/**
	 * add or update  setter config 
	 * @Title: setProperty   
	 * @param key 传入的key
	 * @param value 设置的值
	 * @return void
	 */
	public void setProperty(String key,Object value);
	
	/**
	 * add or update setter config 
	 * @Title: setProperty   
	 * @param map 设置的map集合
	 * @return void
	 */
	public void setProperty(Map<String, Object> map);
}
