package com.mhuang.kafka.common.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @ClassName:  KafkaMsg   
 * @Description:发送消息
 * @author: mhuang
 * @date:   2017年9月20日 下午4:15:03
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KafkaMsg implements Serializable {

	private static final long serialVersionUID = 1L;

	private String topic;
	
	private Long offset;
	
	private Object msg;
}
