package com.mhuang.kafka.common.consumer.event;

/**
 * @package: com.mhuang.kafka.common.consumer.event
 * @author: mhuang
 * @Date: 2018/11/28 14:49
 * @Description:
 */
public enum ConsumerAction {
    /**
     *   暂停
     *   mut not supported
     */
    @Deprecated
    PAUSE,
    /**
     * 继续
     * mut not supported
     */
    @Deprecated
    RESUME,
    /**
     * 销毁
     * @version:
     */
    DESTROY,
    /**
     * 新增
     */
    ADD
}
