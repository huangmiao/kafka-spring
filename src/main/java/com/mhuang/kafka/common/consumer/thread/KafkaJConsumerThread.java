package com.mhuang.kafka.common.consumer.thread;

import com.mhuang.kafka.common.bean.ConsumerBean;
import com.mhuang.kafka.common.bean.KafkaCallbackBean;
import com.mhuang.kafka.common.bean.KafkaMsg;
import com.mhuang.kafka.common.consumer.event.ConsumerAction;
import com.mhuang.kafka.common.consumer.event.ConsumerEvent;
import com.mhuang.kafka.common.exception.JKafkaException;
import com.mhuang.kafka.common.utils.SpringContextHolder;
import com.mhuang.kafka.common.utils.SpringExecute;
import lombok.Setter;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ClassName: KafkaJConsumerPool
 * @Description:消费线程池
 * @author: mhuang
 * @date: 2017年9月18日 上午11:35:54
 */
public class KafkaJConsumerThread implements Runnable, ApplicationListener<ConsumerEvent> {

    @Setter
    private String topic;

    @Setter
    private List<TopicPartition> partition;

    @Setter
    private Map<String, Object> consumerMap;

    @Setter
    private ConsumerBean consumerBean;

    private AtomicBoolean running = new AtomicBoolean(true);

    @Setter
    private KafkaConsumer<Object, Object> consumer;


    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * process pull
     */
    private void start() {
        try {
            logger.info("start consumer properties:{}", consumerMap);
            consumer = new KafkaConsumer<>(consumerMap);
            consumer.assign(partition);
            Boolean isCallBackMethod = !StringUtils.isEmpty(consumerBean.getInvokeCallback());

            while (running.get()) {
                ConsumerRecords<Object, Object> records = consumer.poll(consumerBean.getPull());

                records.forEach(record -> {
                    logger.info("kafka反馈给系统的数据是:{}", record);
                    KafkaMsg kafkaMsg = new KafkaMsg(record.topic(), record.offset(), record.value());
                    try {
                        SpringExecute.getMethodToValue(consumerBean.getInvokeBeanName(),
                                consumerBean.getInvokeMethodName(), kafkaMsg);
                    } catch (Exception e) {
                        logger.error("消费数据异常", e);
                    }
                });

                if (isCallBackMethod && !records.isEmpty()) {
                    consumer.commitAsync((offsets, ex) -> {
                        List<KafkaCallbackBean> listBean = new ArrayList<>(offsets.size());
                        offsets.forEach((parition, metadata) -> {
                            KafkaCallbackBean callBackBean = new KafkaCallbackBean(
                                    parition.topic(),
                                    metadata.offset(),
                                    ex
                            );
                            listBean.add(callBackBean);
                        });
                        try {
                            SpringExecute.getMethodToValue(
                                    consumerBean.getInvokeBeanName(),
                                    consumerBean.getInvokeCallback(),
                                    listBean
                            );
                        } catch (Exception e) {
                            logger.error("消费缺人数据异常", e);
                        }
                    });
                } else {
                    consumer.commitAsync();
                }
            }
        } catch (Exception e) {
            throw new JKafkaException(e.getMessage(), e);
        } finally {
            //避免异常情况、若consumer 为空则是异常
            if (consumer != null) {
                consumer.commitAsync();
                consumer.close();
            }
            logger.info("consumer {} close", topic);
        }
    }

    @Override
    public void run() {
        //use consumer status for start
        start();
    }

    @Override
    public void onApplicationEvent(ConsumerEvent event) {
        if (event.getAction() == ConsumerAction.DESTROY) {
            destroy(event.getParams());
        }
    }

    private void destroy(Map<String, Object> params) {
        Object destroyPartition = params.get(topic);
        if (destroyPartition != null) {
            logger.info("destory current topic:{} satisfied,params:{} ", topic, params);
            running.compareAndSet(true, false);
            SpringContextHolder.removeBean(String.valueOf(consumerMap.get("group.id")));
            logger.info("destory current topis:{},beanId:{}", topic, consumerMap.get("group.id"));
        }
    }
}
