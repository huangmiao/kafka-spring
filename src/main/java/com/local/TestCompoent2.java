//package com.local;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import com.mhuang.kafka.common.producer.send.IKafkaJProducer;
//
//@Component
//@Order(value = 10000)
//public class TestCompoent2 implements InitializingBean{
//
//	@Autowired
//	@Qualifier("proname1")
//	private IKafkaJProducer<String, String> producer;
//	
//	@Autowired
//	@Qualifier("proname2")
//	private IKafkaJProducer<String, String> producer2;
//	
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		
//		producer.send("test","1","1",(k,v)->{
//			System.out.println(k);
//			System.out.println(v);
//		});
//		
//		producer2.send("test","1","1",(k,v)->{
//			System.out.println(k);
//			System.out.println(v);
//		});
//	}
//}
