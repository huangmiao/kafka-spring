package com.mhuang.kafka.common.producer.ob;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @ClassName:  ProducerSubject   
 * @Description:生产者订阅接口   
 * @author: mhuang
 * @date:   2017年9月15日 下午3:37:53
 */
public abstract class AbstractProducerSubject implements ProducerSubjetMethod{

	Map<String, ProducerObServer> proObMap = new ConcurrentHashMap<>();
	
	/** 
     * create ob author
     */  
	@Override
    public ProducerObServer add(String key,ProducerObServer obs){ 
    	ProducerObServer ob = null;
    	if(!proObMap.containsKey(key)){
    		ob = proObMap.put(key, obs);
    	}
		return ob;
    }  
    
	@Override
    public ProducerObServer remove(String key){
    	ProducerObServer ob = null;
    	if(proObMap.containsKey(key)){
    		ob = proObMap.remove(key);
    	}
    	return ob;
    }
    
}
