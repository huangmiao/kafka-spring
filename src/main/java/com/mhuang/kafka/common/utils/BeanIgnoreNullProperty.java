package com.mhuang.kafka.common.utils;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 
 * @ClassName:  BeanIgnoreNullProperty   
 * @Description: 过滤Null
 * @author: mhuang
 * @date:   2017年9月18日 上午11:05:32
 */
public class BeanIgnoreNullProperty {
	
	/**
	 * 
	 * @Title: getNullPropertyNames   
	 * @Description: 获取为空的属性名
	 * @param source
	 * @return
	 * @return String[]
	 */
	public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null){
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}