package com.mhuang.kafka.common.bean;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * 
 * @ClassName:  KafkaProperty   
 * @Description:kafka配置
 * @author: mhuang
 * @date:   2017年9月12日 下午1:39:13
 */
@Data
public class KafkaBean {

	@JSONField(name = "bootstrap.servers")
	private String servers ;
}
