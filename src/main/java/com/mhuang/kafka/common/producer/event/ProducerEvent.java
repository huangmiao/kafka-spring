package com.mhuang.kafka.common.producer.event;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName:  ProducerEvent   
 * @Description:生产者通知事件
 * @author: mhuang
 * @date:   2017年9月15日 下午4:16:27
 */
public class ProducerEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;
	
	@Setter
	@Getter
	private Map<String, Object> map;
	
	public ProducerEvent(Object source,Map<String, Object> map) {
		super(source);
		this.map = map;
	}
	
	public ProducerEvent(Object source,String key,Object value) {
		super(source);
		map = new HashMap<>(1);
		map.put(key, value);
	}
}
