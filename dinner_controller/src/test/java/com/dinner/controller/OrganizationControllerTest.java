package com.dinner.controller;

import java.io.IOException;

import org.junit.Test;

import com.dinner.controller.user.LoginController;
import com.dinner.controller.user.OrganizationController;

import net.sf.json.JSONObject;  
  
public class OrganizationControllerTest extends AbstractDbControllerTest<OrganizationController> {  
  
    @Test  
    public void testRegistEnt() throws IOException {  
    
        String retString = controller.registEnterprise("8888", "8888", "8888@qq.com", "111111",null, httpSession);
        JSONObject jsonObject = JSONObject.fromObject(retString);
        System.out.println(retString);
//        if(jsonObject.getString("code").equals("0")){
//        	LoginController loginController = (LoginController) this.applicationContext.getBean(LoginController.class);  
//	        String password = ((JSONObject)jsonObject.get("userVo")).getString("password");
//	        retString = loginController.login("8888@qq.com", password, "1111", httpSession); 
//	        System.out.println(retString);
//        }
    }  
  
}  
