package com.mhuang.kafka.common.producer.ob;

/**
 * 
 * @ClassName:  ProducerSubjectMethod   
 * @Description:生产者订阅方法
 * @author: mhuang
 * @date:   2017年9月15日 下午3:39:59
 */
public interface ProducerSubjetMethod {

	/**
	 * remove ob author   
	 * @Title: add   
	 * @param key
	 * @param obs
	 * @return ProducerObServer
	 */
	ProducerObServer add(String key,ProducerObServer obs);
	
	/**
	 * remove ob author to key
	 * @Title: remove   
	 * @param key
	 * @param obs
	 * @return ProducerObServer
	 */
	ProducerObServer remove(String key);
}
