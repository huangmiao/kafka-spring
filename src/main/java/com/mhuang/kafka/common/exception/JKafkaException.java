package com.mhuang.kafka.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName:  JKafkaException   
 * @Description:异常
 * @author: mhuang
 * @date:   2017年9月14日 下午2:06:16
 */
public class JKafkaException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * 信息
	 */
	@Setter
	@Getter
	private String message;
	
	/**
	 * 异常
	 */
	@Setter
	@Getter
	private Throwable cause; 
	
	public JKafkaException(String message){
		super(message);
		this.message = message;
	}
	
	public JKafkaException(String message,Throwable cause){
		super(message,cause);
		this.message = message;
		this.cause = cause;
	}
}
