package com.mhuang.kafka.common.consumer.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @package: com.mhuang.kafka.common.consumer.event
 * @author: mhuang
 * @Date: 2018/11/28 14:02
 * @Description:
 */
@Data
public class ConsumerEvent extends ApplicationEvent {

    /**
     * 动作
     *
     */
    private ConsumerAction action;
    /**
     * 参数
     * 根据动作所执行操作
     */
    private Map<String,Object> params;

    public ConsumerEvent(Object source){
        super(source);
    }

    public ConsumerEvent(Object source, ConsumerAction action, Map<String,Object> params) {
        super(source);
        this.action = action;
        this.params = params;
    }
}
