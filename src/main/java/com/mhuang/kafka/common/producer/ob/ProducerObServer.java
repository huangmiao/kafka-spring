package com.mhuang.kafka.common.producer.ob;

import java.util.Map;
import java.util.Properties;

/**
 * 
 * @ClassName:  ProducerObServer   
 * @Description:生产者观察
 * @author: mhuang
 * @date:   2017年9月15日 上午11:06:02
 */
public interface ProducerObServer {

    /**
     * 通知所有配置
     * @Title: notifyAllProp   
     * @param properties 配置
     * @return boolean 通知成功或者失败
     */
	public boolean notifyAllProp(Properties properties);

	/**
	 *  通知所有配置
	 * @Title: modfiyAllProp   
	 * @param map 传入集合
	 * @return boolean 通知成功或者失败
	 */
	public boolean notifyAllProp(Map<String, Object> map);

	/**
	 * 通知单个配置生效
	 * @Title: notifyProp   
	 * @param key 通知的key
	 * @param value 通知的value
	 * @return boolean 成功或者失败
	 */
	public boolean notifyProp(String key,Object value);
}
