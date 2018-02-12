package com.mhuang.kafka.common.constans;

/**
 * 
 * @ClassName:  KafkaGlobal   
 * @Description:全局
 * @author: mhuang
 * @date:   2017年9月14日 下午1:52:49
 */
public class KafkaGlobal {

	//kafka info 
    /**
     * kafkaInfo
     */
	public final static String KAFKA_INFO = "kafkaInfo";
	public final static String KAFKAINFO_SERVERS = "servers";
	public final static String KAFKAINFO_KEY_SERIALIZER = "keySerializer";
	public final static String KAFKAINFO_VLAUE_SERIALIZER = "valueSerializer";
	public final static String KAFKAINFO_KEY_DESERIALIZER = "keyDeserializer";
	public final static String KAFKAINFO_VLAUE_DESERIALIZER = "valueDeserializer";
	public final static String KAFKAINFO_PRODUCERINFO = "producerInfo";
	public final static String KAFKAINFO_CONSUMERINFO = "consumerInfo";
	public final static String KAFKAINFO_ENABLE_PRODUCER = "enableProducer";
	public final static String KAFKAINFO_ENABLE_CONSUMER = "enableConsumer";
	
	//common
	/**
	 * kafka.
	 */
	public final static String FIELD_KAFKA = "kafka.";
	public final static String FIELD_ENABLE = "enable";
	public final static String FIELD_NAMES = "names";
	
	public final static String PROP = "prop";
	public final static String NAME = "name";
	
	public final static String FIELD_BOOTSTRAP_SERVERS = "bootstrap.servers";
	//序列化key默认配置
	/**
	 * key.serializer
	 */
	public final static String FIELD_KEY_SERIALIZER = "key.serializer";
	public final static String FIELD_KEY_DESERIALIZER = "key.deserializer";
	public final static String FIELD_KEY_SERIALIZER_DEFAULT = "org.apache.kafka.common.serialization.StringSerializer";
	public final static String FIELD_KEY_DESERIALIZER_DEFAULT = "org.apache.kafka.common.serialization.StringDeserializer";
	//序列化value默认配置
	/**
	 * value.serializer
	 */
	public final static String FIELD_VALUE_SERIALIZER = "value.serializer";
	public final static String FIELD_VALUE_DESERIALIZER = "value.deserializer";
	public final static String FIELD_VALUE_SERIALIZERDEFAULT = "org.apache.kafka.common.serialization.StringSerializer";
	public final static String FIELD_VALUE_DESERIALIZERDEFAULT = "org.apache.kafka.common.serialization.StringDeserializer";
	///////////////////////producer
	/**
	 * producer
	 */
	public final static String FIELD_PRODUCER = "producer";
	//是否启用
	/**
	 * FIELD_PRODUCER_ENABLE
	 */
	public final static String FIELD_PRODUCER_ENABLE = FIELD_PRODUCER + "." + FIELD_ENABLE;
	public final static String FIELD_PRODUCER_NAMES = FIELD_PRODUCER + "." + FIELD_NAMES;
	public final static String FIELD_PRODUCER_ACKS = "acks";
	public final static String FIELD_PRODUCER_ACKS_DEFAULT = "all";
	public final static String FIELD_PRODUCER_RETRIES = "retries";
	public final static String FIELD_PRODUCER_RETRIES_DEFAULT = "0";
	public final static String FIELD_PRODUCER_BATCH_SIZE = "batch.size";
	public final static Integer FIELD_PRODUCER_BATCH_SIZE_DEFAULT = 16384;
	public final static String FIELD_PRODUCER_LINGERMS = "linger.ms";
	public final static Long FIELD_PRODUCER_LINGERMS_DEFAULT = 1L;
	public final static String FIELD_PRODUCER_BUFFER_MEMORY = "buffer.memory";
	public final static Long FIELD_PRODUCER_BUFFER_MEMORY_DEFAULT = 33554432L;
	public final static String FIELD_PRODUCER_PARTITIONER_CLASS = "partitioner.class";
	public final static String FIELD_PRODUCER_PARTITIONER_CLASS_FEFAULT = "com.mhuang.kafka.common.partition.HashPartition";
	
	////consumer
	/**
	 * consumer
	 */
	public final static String FIELD_CONSUMER = "consumer";
	public final static String FIELD_CONSUMER_ENABLE = FIELD_CONSUMER + "." + FIELD_ENABLE;
	public final static String FIELD_CONSUMER_NAMES = FIELD_CONSUMER + "." + FIELD_NAMES;
	public final static String FIELD_CONSUMER_GROUP_ID = "group.id";
	public final static String FIELD_CONSUMER_ENABLE_AUTO_COMMIT = "enable.auto.commit";
	public final static Boolean FIELD_CONSUMER_ENABLE_AUTO_COMMIT_DEFAULT = false;
	public final static String FIELD_CONSUMER_AUTO_COMMIT_INTERVAL_MS = "auto.commit.interval.ms";
	public final static Integer FIELD_CONSUMER_AUTO_COMMIT_INTERVAL_MS_DEFAULT = 3000;
	public final static String FIELD_CONSUMER_SESSION_TIMEOUT_MS = "session.timeout.ms";
	public final static Integer FIELD_CONSUMER_SESSION_TIMEOUT_MS_DEFAULT = 30000;
	public final static String FIELD_CONSUMER_AUTO_OFFSET_RESET = "auto.offset.reset";
	public final static String FIELD_CONSUMER_AUTO_OFFSET_RESET_DEFAULT = "latest";
	public final static String FIELD_CONSUMER_PULL  = "pull";
	public final static Integer FIELD_CONSUMER_PULL_DEFAULT  = 200;
	public final static String FIELD_CONSUMER_INVOKE_BEAN = "invoke.bean";
	public final static String FIELD_CONSUMER_INVOKE_METHOD = "invoke.method";
}
