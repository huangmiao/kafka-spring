package com.mhuang.kafka.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * @ClassName:  SpringExecute   
 * @Description:spring工具类 
 * @author: mhuang
 * @date:   2018年1月26日 下午3:16:53
 */
public class SpringExecute extends AbstractInvokeExecute{

	/**
	 * 
	 * @param beanName spring調用的beanString
	 * @param methodName 调用的class类的方法
	 * @return 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getMethodToValue(String beanName,
			String methodName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		Object bean =  SpringContextHolder.getBean(beanName);
		Method method = bean.getClass().getMethod(methodName);
		return (T) method.invoke(bean);
	}
	
	/**
	 * 
	 * @param beanName 调用spring 的beanString
	 * @param methodName 调用的class类的方法
	 * @param param 传递的参数
	 * @return 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getMethodToValue(String beanName,
			String methodName,Object param) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		Object bean =  SpringContextHolder.getBean(beanName);
		Method method = bean.getClass().getMethod(methodName, checkType(param));
		return (T) method.invoke(bean,param);
	}
	/**
	 * 
	 * @param beanName 调用的Spring的beanString
	 * @param methodName 调用的方法 
	 * @param params 调用的参数。
	 * @return 返回类型
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getMethodToValue(String beanName,
		String methodName,
		Object... params) 
		throws NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException, InstantiationException{
		Object bean =  SpringContextHolder.getBean(beanName);
		Class<?>[] clazzes = new Class[params.length];
		int index = 0;
		for(Object obj : params){
			clazzes[index++] = checkType(obj);
		}
		Method method = bean.getClass().getMethod(methodName, clazzes);
		return (T) method.invoke(bean,params);
	}
}