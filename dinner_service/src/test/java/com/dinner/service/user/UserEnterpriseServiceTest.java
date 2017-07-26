package com.dinner.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Author:nianrong.fang@docqian.com
 * Date: 2015/12/31
 * Time:10:11
 * Copyrite(c): docqian.com
 * Project:docqian
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/dinner-db.xml" })
public class UserEnterpriseServiceTest {

	@Autowired
	private UserEnterpriseService userEnterpriseService;

	@Test
	public void testRegistEnterprise(){
		String entName = "文屏网络有限公司";
		String userName = "文平";
		String email = "wp@163.com";
		String phone = "13111111111";
		String comment = "";
		userEnterpriseService.registEnterprise(entName, userName, email, phone, comment);
	}
}
