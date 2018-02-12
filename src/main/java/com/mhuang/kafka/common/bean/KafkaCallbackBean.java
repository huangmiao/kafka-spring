package com.mhuang.kafka.common.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @ClassName:  KafkaCallbackBean   
 * @Description:kafka回掉bean实体
 * @author: mhuang
 * @date:   2018年1月26日 下午3:16:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaCallbackBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String topic;
	
	private Long offset;
	
	private Exception e;
}
