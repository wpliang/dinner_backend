package com.dinner.controller;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
* @ClassName: AbstractDbControllerTest
* @Description: 数据来源是DB里面的，运行后数据会保存在DB里面
* @author wp.liang
* @date 2017年7月26日 下午4:18:05
* 
* @param <T>
 */
@ContextConfiguration(locations = { "classpath:spring/dinner-db.xml",  
        "classpath:spring/dinner-web.xml" })  
@RunWith(SpringJUnit4ClassRunner.class) 	
public class AbstractDbControllerTest<T> extends AbstractJUnit4SpringContextTests implements BaseControllerTest<T> {  
	protected T controller;
	protected HttpSession httpSession;
	
	public String fillRequestMehod(){
		return "POST";
	}
	
	@Before
	public void init(){
		controller = getController(this.applicationContext);
		if("get".equalsIgnoreCase(fillRequestMehod())){
			httpSession = getGetHttpSession();
		} else {
			httpSession = getPostHttpSession();
		}
	}
}  