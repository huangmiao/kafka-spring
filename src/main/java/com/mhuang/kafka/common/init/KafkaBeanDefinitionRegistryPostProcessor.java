package com.mhuang.kafka.common.init;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.core.env.Environment;

import com.alibaba.fastjson.JSONObject;
import com.mhuang.common.spring.properties.RelaxedPropertyResolver;
import com.mhuang.kafka.common.bean.ConsumerBean;
import com.mhuang.kafka.common.bean.KafkaInfo;
import com.mhuang.kafka.common.bean.ProducerBean;
import com.mhuang.kafka.common.constans.KafkaGlobal;
import com.mhuang.kafka.common.consumer.rec.KafkaJConsumer;
import com.mhuang.kafka.common.producer.send.KafkaJProducer;

/**
 * 
 * @ClassName:  KafkaBeanDefinitionRegistryPostProcessor   
 * @Description:Bean初始化
 * @author: mhuang
 * @date:   2017年9月14日 上午10:08:33
 */
@Configuration
public class KafkaBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor,
	EnvironmentAware{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private KafkaInfo kafkaInfo = new KafkaInfo();
	
	private Map<String, Map<String,Object>> kafkaProducerMap = new HashMap<>();
	private Map<String, Map<String,Object>> kafkaConsumerMap = new HashMap<>();
	private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
    
    @Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		logger.info("init process bean factory");
		
		BeanDefinition kafkaBD = beanFactory.getBeanDefinition(KafkaGlobal.KAFKA_INFO);
		MutablePropertyValues kafkaMpv = kafkaBD.getPropertyValues();
		
		kafkaMpv.add(KafkaGlobal.KAFKAINFO_SERVERS, kafkaInfo.getServers());
		kafkaMpv.add(KafkaGlobal.KAFKAINFO_KEY_SERIALIZER, kafkaInfo.getKeySerializer());
		kafkaMpv.add(KafkaGlobal.KAFKAINFO_VLAUE_SERIALIZER, kafkaInfo.getValueSerializer());
		kafkaMpv.add(KafkaGlobal.KAFKAINFO_KEY_DESERIALIZER, kafkaInfo.getKeyDeserializer());
		kafkaMpv.add(KafkaGlobal.KAFKAINFO_VLAUE_DESERIALIZER, kafkaInfo.getValueDeserializer());
		kafkaMpv.add(KafkaGlobal.KAFKAINFO_ENABLE_PRODUCER, kafkaInfo.getEnableProducer());
		kafkaMpv.add(KafkaGlobal.KAFKAINFO_ENABLE_CONSUMER, kafkaInfo.getEnableConsumer());
		kafkaMpv.add(KafkaGlobal.KAFKAINFO_PRODUCERINFO, kafkaInfo.getProducerInfo());
		kafkaMpv.add(KafkaGlobal.KAFKAINFO_CONSUMERINFO, kafkaInfo.getConsumerInfo());
		
		kafkaProducerMap.forEach((key,map)->{
			BeanDefinition bd = beanFactory.getBeanDefinition(key);
			MutablePropertyValues mpv = bd.getPropertyValues();
			Properties props = new Properties();
			props.putAll(map);
			mpv.add(KafkaGlobal.PROP, props);
			mpv.add(KafkaGlobal.NAME, key);
		});
		
		kafkaConsumerMap.forEach((key,map)->{
			BeanDefinition bd = beanFactory.getBeanDefinition(key);
			MutablePropertyValues mpv = bd.getPropertyValues();
			mpv.add(KafkaGlobal.NAME, key);
		});
		
		logger.info("success factory bean");
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		logger.info("init process bean register");
		
		kafkaInfo.getProducerInfo().forEach((key,producer)->{
			registerBean(registry,key ,KafkaJProducer.class);
		});
		
		kafkaInfo.getConsumerInfo().forEach((key,consumer)->{
			registerBean(registry,key,KafkaJConsumer.class);
		});
		 
		registerBean(registry, KafkaGlobal.KAFKA_INFO, KafkaInfo.class);
		
		logger.info("success bean register");
	}

	/**
	 * 
	 * @Title: registerBean   
	 * @Description: 注册bean
	 * @param registry
	 * @param name
	 * @param beanClass
	 * @return void
	 */
	private void registerBean(BeanDefinitionRegistry registry, String name, Class<?> beanClass){
		logger.info("init register bean");
		AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);

        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
        abd.setScope(scopeMetadata.getScopeName());
        // 可以自动生成name
        String beanName = (name != null ? name : this.beanNameGenerator.generateBeanName(abd, registry));
        
        AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);

        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
        logger.info("success register bean");
        
	}

	@Override
	public void setEnvironment(Environment env) {
		
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, KafkaGlobal.FIELD_KAFKA);
		//proccess kafka common config properties
		proccessConfig(propertyResolver);
		
		//proccess customer config properties
		proccessCustomerConfig(propertyResolver);
		
		//proccess producer config properties
		proccessProducerConfig(propertyResolver);
	}
    
    /**
	 * 
	 * @Title: proccessConfig   
	 * @Description: 处理kafka通用配置
	 * @param propertyResolver
	 * @return void
	 */
	private void proccessConfig(RelaxedPropertyResolver propertyResolver){
		
		String servers = propertyResolver.getProperty(KafkaGlobal.FIELD_BOOTSTRAP_SERVERS),
			keySerializer = propertyResolver.getProperty(KafkaGlobal.FIELD_KEY_SERIALIZER,KafkaGlobal.FIELD_KEY_SERIALIZER_DEFAULT),
			keyDeserializer = propertyResolver.getProperty(KafkaGlobal.FIELD_KEY_DESERIALIZER,KafkaGlobal.FIELD_KEY_DESERIALIZER_DEFAULT),
			valueSerializer = propertyResolver.getProperty(KafkaGlobal.FIELD_VALUE_SERIALIZER,KafkaGlobal.FIELD_VALUE_SERIALIZERDEFAULT),
			valueDeserializer = propertyResolver.getProperty(KafkaGlobal.FIELD_VALUE_DESERIALIZER,KafkaGlobal.FIELD_VALUE_DESERIALIZERDEFAULT);
		
		kafkaInfo.setServers(servers);
		kafkaInfo.setKeySerializer(keySerializer);
		kafkaInfo.setValueSerializer(valueSerializer);
		kafkaInfo.setKeyDeserializer(keyDeserializer);
		kafkaInfo.setValueDeserializer(valueDeserializer);
	}
	
	/**
	 * 
	 * @Title: proccessCustomerConfig  
	 * @Description: 处理消费配置
	 * @param propertyResolver
	 * @return void
	 */
	private void proccessCustomerConfig(RelaxedPropertyResolver propertyResolver){
		logger.info("init kafka customer config");
		Boolean customerEnable = propertyResolver.getProperty(KafkaGlobal.FIELD_CONSUMER_ENABLE,Boolean.class,false);
		kafkaInfo.setEnableConsumer(customerEnable);
		logger.info("kafka.customer.enable : {}",customerEnable);
		if(customerEnable){
			String names = propertyResolver.getRequiredProperty(KafkaGlobal.FIELD_CONSUMER_NAMES);
			proccessPropertice(names,KafkaGlobal.FIELD_CONSUMER,propertyResolver);
			logger.info("process customer config");
		}
		logger.info("success kafka cunstomer config");
	}
	
	/**
	 * 
	 * @Title: proccessProducerConfig   
	 * @Description: 处理生产配置
	 * @param propertyResolver
	 * @return void
	 */
	private void proccessProducerConfig(RelaxedPropertyResolver propertyResolver){
		logger.info("init kafka producer config");
		Boolean producerEnable = propertyResolver.getProperty(KafkaGlobal.FIELD_PRODUCER_ENABLE,Boolean.class,true);
		kafkaInfo.setEnableProducer(producerEnable);
		logger.info("kafka.producer.enable : {}",producerEnable);
		if(producerEnable){
			String names = propertyResolver.getRequiredProperty(KafkaGlobal.FIELD_PRODUCER_NAMES);
			proccessPropertice(names,KafkaGlobal.FIELD_PRODUCER,propertyResolver);
			logger.info("process producer config");
		}
		logger.info("success kafka producer config");
	}
	
	/**
	 * 
	 * @Title: proccessProducerConfig   
	 * @Description: 解析处理kafka配置 
	 * @param names 解析的多列表
	 * @param type	解析类型（消费 or 生产）
	 * @param propertyResolver 
	 * @return void
	 */
	private void proccessPropertice(String names,String type,RelaxedPropertyResolver propertyResolver){
		String[] nameArr = names.split(",");
		if(nameArr  == null){
			proccessNamePropertice(names,type,propertyResolver);
		}else{
			for(String name:nameArr){
		        proccessNamePropertice(name,type,propertyResolver);
			}
		}
	}
	
	private void proccessNamePropertice(String name,String type,RelaxedPropertyResolver propertyResolver){
		StringBuilder subProperyKey = new StringBuilder(type);
		logger.info("loading {} properties-->{}",type,name);
		subProperyKey.append(".").append(name).append(".");
		
		Map<String, Object> nameProperty = propertyResolver.getSubProperties(subProperyKey.toString());
		
		Map<String, Object> map = new HashMap<>(nameProperty);
		map.putIfAbsent(KafkaGlobal.FIELD_BOOTSTRAP_SERVERS, kafkaInfo.getServers());
		//save current Map
		if(KafkaGlobal.FIELD_PRODUCER.equals(type)){
			convertProducerMap(map);
			kafkaInfo.getProducerInfo().put(name, 
				new JSONObject(map).toJavaObject(ProducerBean.class)
			);
			kafkaProducerMap.put(name, map);
		}else{
			convertConsumerMap(map);
			kafkaInfo.getConsumerInfo().put(name, 
				new JSONObject(map).toJavaObject(ConsumerBean.class)
			);
			kafkaConsumerMap.put(name, map);
		}
		
		logger.info("loading {}:{} data:{}",name,type,nameProperty);
		logger.info("loading {} properties --> {} sucess",type,name);
	}
	
	private void convertConsumerMap(Map<String,Object> map){
        map.putIfAbsent(KafkaGlobal.FIELD_KEY_DESERIALIZER, kafkaInfo.getKeyDeserializer());
		map.putIfAbsent(KafkaGlobal.FIELD_VALUE_DESERIALIZER, kafkaInfo.getValueDeserializer());
		map.putIfAbsent(KafkaGlobal.FIELD_CONSUMER_PULL, KafkaGlobal.FIELD_CONSUMER_PULL_DEFAULT);
		map.putIfAbsent(KafkaGlobal.FIELD_CONSUMER_ENABLE_AUTO_COMMIT, KafkaGlobal.FIELD_CONSUMER_ENABLE_AUTO_COMMIT_DEFAULT);
		map.putIfAbsent(KafkaGlobal.FIELD_CONSUMER_SESSION_TIMEOUT_MS, KafkaGlobal.FIELD_CONSUMER_SESSION_TIMEOUT_MS_DEFAULT);
		map.putIfAbsent(KafkaGlobal.FIELD_CONSUMER_AUTO_COMMIT_INTERVAL_MS, KafkaGlobal.FIELD_CONSUMER_AUTO_COMMIT_INTERVAL_MS_DEFAULT);
		map.putIfAbsent(KafkaGlobal.FIELD_CONSUMER_AUTO_OFFSET_RESET, KafkaGlobal.FIELD_CONSUMER_AUTO_OFFSET_RESET_DEFAULT);
		map.putIfAbsent(KafkaGlobal.FIELD_CONSUMER_THREAD_PARTITION, KafkaGlobal.FIELD_CONSUMER_THREAD_PARTITION_DEFAULT);
	}
	/**
	 * 
	 * @Title: producerMapToBean   
	 * @Description: 生产map转成bean   
	 * @param map
	 * @return void
	 */
	private void convertProducerMap(Map<String, Object> map){
		map.putIfAbsent(KafkaGlobal.FIELD_KEY_SERIALIZER, kafkaInfo.getKeySerializer());
		map.putIfAbsent(KafkaGlobal.FIELD_VALUE_SERIALIZER, kafkaInfo.getValueSerializer());
		map.putIfAbsent(KafkaGlobal.FIELD_PRODUCER_ACKS, KafkaGlobal.FIELD_PRODUCER_ACKS_DEFAULT);
		map.putIfAbsent(KafkaGlobal.FIELD_PRODUCER_RETRIES, KafkaGlobal.FIELD_PRODUCER_RETRIES_DEFAULT);
		map.putIfAbsent(KafkaGlobal.FIELD_PRODUCER_BATCH_SIZE, KafkaGlobal.FIELD_PRODUCER_BATCH_SIZE_DEFAULT);
		map.putIfAbsent(KafkaGlobal.FIELD_PRODUCER_LINGERMS, KafkaGlobal.FIELD_PRODUCER_LINGERMS_DEFAULT);
		map.putIfAbsent(KafkaGlobal.FIELD_PRODUCER_BUFFER_MEMORY, KafkaGlobal.FIELD_PRODUCER_BUFFER_MEMORY_DEFAULT);
		map.putIfAbsent(KafkaGlobal.FIELD_PRODUCER_PARTITIONER_CLASS, KafkaGlobal.FIELD_PRODUCER_PARTITIONER_CLASS_FEFAULT);
		

	}
}
