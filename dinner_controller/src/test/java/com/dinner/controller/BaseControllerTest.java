package com.dinner.controller;

import java.lang.reflect.ParameterizedType;

import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;

public interface BaseControllerTest<T> {
	
	default HttpSession getHttpSession(String requestMethod){
		MockHttpServletRequest request = new MockHttpServletRequest();  
	    request.setMethod(requestMethod);  
		return request.getSession();
	}
	
	default HttpSession getPostHttpSession(){
		return getHttpSession("POST");
	}
	
	default HttpSession getGetHttpSession(){
		return getHttpSession("GET");
	}
	
	@SuppressWarnings("unchecked")
	default T getController(ApplicationContext applicationContext){
		Class <T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return applicationContext.getBean(entityClass);
	}
}
