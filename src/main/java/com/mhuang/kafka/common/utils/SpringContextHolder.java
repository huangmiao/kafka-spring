package com.mhuang.kafka.common.utils;

import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName:  SpringContextHolder   
 * @Description:Spring上下文  
 * @author: mhuang
 * @date:   2017年9月18日 下午6:59:33
 */
@Component
public class SpringContextHolder implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext;  
	
	private static DefaultListableBeanFactory beanFacotory;
	
    /**
     * 获取上下文 
     * @Title: getApplicationContext   
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
  
    /**
     * 上下文初始化
     * <p>Title: setApplicationContext</p>   
     * <p>Description: </p>   
     * @param applicationContext   
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
    	if(SpringContextHolder.applicationContext == null){
    		SpringContextHolder.applicationContext = applicationContext;
    		beanFacotory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
    	}
    		  
    }  
  
    /**
     * 通过名字获取上下文中的bean 
     * @Title: getBean   
     * @param name
     * @return
     * @return Object
     */
    public static Object getBean(String name){  
        return applicationContext.getBean(name);  
    }
    
    /**
     * 通过名字获取上下文中的bean  
     * @Title: getBean   
     * @param name
     * @param clazz
     * @return
     * @return T
     */
    public static <T> T getBean(String name,Class<T> clazz){  
        return applicationContext.getBean(name,clazz);  
    }
    
    /**
     * 通过名字删除上下文的bean
     * @Title: removeBean   
     * @param name
     * @return void
     */
    public static void removeBean(String name){
    	beanFacotory.removeBeanDefinition(name);
    }
    
    
    /**
     * bean注册
     * @Title: registerBean   
     * @param beanName
     * @param clazz
     * @param params
     * @return
     * @return T
     */
    public static <T> T registerBean(String beanName,Class<T> clazz,Map<String, Object> params){
    	BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
    	
    	params.forEach((key,value)->{
    		beanDefinitionBuilder.addPropertyValue(key, value);
    	});
    	beanFacotory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
    	
    	return getBean(beanName, clazz);
    }
    
    /**
     * 
     * 根据class获取上下文的bean
     * @Title: getBean   
     * @param requiredType
     * @return
     * @return Object
     */
    public static Object getBean(Class<?> requiredType){  
        return applicationContext.getBean(requiredType);  
    }
}  