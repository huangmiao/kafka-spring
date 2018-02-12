//package com.local;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSON;
//import com.mhuang.kafka.common.bean.KafkaCallbackBean;
//import com.mhuang.kafka.common.bean.KafkaInfo;
//import com.mhuang.kafka.common.bean.KafkaMsg;
//
//@Component("test")
//public class Test {
//
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Autowired
//	KafkaInfo kafkaInfo;
//	
//	public void test(KafkaMsg kafkaMsg){
//		logger.debug(JSON.toJSONString(kafkaMsg));
//	}
//	
//	public void callback(List<KafkaCallbackBean> beanList){
//		System.out.println(beanList);
//	}
//}
