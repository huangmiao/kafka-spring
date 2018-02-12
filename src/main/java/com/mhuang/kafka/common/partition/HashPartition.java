package com.mhuang.kafka.common.partition;

import java.util.Map;
import java.util.Random;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

/**
 * 
 * @ClassName:  HashPartition   
 * @Description:Hash分片
 * @author: mhuang
 * @date:   2017年9月13日 上午11:36:40
 */
public class HashPartition implements Partitioner{

	private Random random = new Random();
	
	@Override
	public void configure(Map<String, ?> configs) {
		
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		//获取当前配置的分片数
		Integer partitions = cluster.partitionCountForTopic(topic);
		//随机获取分片
		return random.nextInt(partitions);
	}

	@Override
	public void close() {
		
	}

}
