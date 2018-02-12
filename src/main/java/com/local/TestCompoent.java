//package com.local;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//import com.mhuang.kafka.common.bean.KafkaInfo;
//import com.mhuang.kafka.common.notify.KafkaNotify;
//import com.mhuang.kafka.common.producer.send.IKafkaJProducer;
//
//@Component
//public class TestCompoent implements InitializingBean{
//
//	@Autowired
//	@Qualifier("proname1")
//	private IKafkaJProducer<String, String> producer;
//	
//	@Autowired
//	@Qualifier("proname2")
//	private IKafkaJProducer<String, String> producer2;
//	
//	@Autowired
//	KafkaNotify kafkaNotify;
//	
//	@Autowired
//	KafkaInfo kafkaInfo;
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		
//		producer.send("test","1","1",(k,v)->{
//			System.out.println(k);
//			System.out.println(v);
//		});
//		
//		
//		producer2.send("test","1","1",(k,v)->{
//			System.out.println(k);
//			System.out.println(v);
//		});
//		
//		kafkaNotify.producerPublish("linger.ms","2");
//		
//		producer.setProperty("linger.ms","2");
//		
//		kafkaInfo.getProducerInfo().forEach((key,value)->{
//			System.out.println(key);
//			System.out.println(value);
//		});
//	}
//
//}
