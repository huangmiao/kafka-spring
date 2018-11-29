package com.mhuang.kafka.common.notify;

import com.mhuang.kafka.common.consumer.event.ConsumerAction;
import com.mhuang.kafka.common.consumer.event.ConsumerEvent;
import com.mhuang.kafka.common.producer.event.ProducerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName: KafkaNotifyServer
 * @Description:kafka通知服务
 * @author: mhuang
 * @date: 2017年9月15日 下午4:46:48
 */
@Component
public class KafkaNotify {

    private final static List<Integer> DEFAULT_PARTITION_LIST = Stream.of(-1).collect(Collectors.toList());
    @Autowired
    ApplicationContext context;

    /**
     * @param map
     * @return void
     * @Title: producerPublish
     * @Description: 通知加载所有生产者的配置
     * 注：若存在相应的配置则修改。没有则添加。
     */
    public void producerPublish(Map<String, Object> map) {
        context.publishEvent(new ProducerEvent(this, map));
    }

    /**
     * @param key   更改的配置名。
     * @param value 更改的值
     * @return void
     * @Title: producerPublish
     * @Description: 通知加载所有生产者的配置（单个配置）
     * 注：若存在相应的配置则修改。没有则添加。
     * 比如bootstrap.servers 代表key。value就是你想要的结果
     */
    public void producerPublish(String key, Object value) {
        context.publishEvent(new ProducerEvent(this, key, value));
    }

    /**
     * 通知
     *
     * @param action
     * @param params
     * @version 0.1.1.0.1
     */
    public void consumerPublish(ConsumerAction action, Map<String, Object> params) {
        context.publishEvent(new ConsumerEvent(this, action, params));
    }

    public void consumerPublish(ConsumerAction action, String... partitions) {
        List<String> partitionList = Stream.of(partitions).collect(Collectors.toList());
        context.publishEvent(
                new ConsumerEvent(this,
                        action,
                        partitionList.stream().collect(
                            Collectors.toMap(String::new, s -> DEFAULT_PARTITION_LIST, (s1, s2) -> s1)
                        )
                )
        );
    }
}
