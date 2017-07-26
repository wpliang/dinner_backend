package com.dinner.controller;


import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dinner.common.base.constant.Constants;
import com.dinner.controller.captcha.CaptchaUtils;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CommonController extends BaseController {

	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void captcha(HttpServletResponse response,HttpSession session)throws Exception {  
		//利用图片工具生成图片  
	    //第一个参数是生成的验证码，第二个参数是生成的图片  
	    Object[] objs = CaptchaUtils.generateImage();  
	    //将验证码存入Session  
	    getCacheService().setValue(session, Constants.USER_CAPTCHA, objs[0]);  
	    //将图片输出给浏览器  
	    BufferedImage image = (BufferedImage) objs[1];  
	    response.setContentType("image/png");  
	    OutputStream os = response.getOutputStream();  
	    ImageIO.write(image, "png", os);  
    } 
	
	@RequestMapping(value = "/sysTime", method = RequestMethod.GET)
	@ResponseBody
    public String sysTime(HttpServletResponse response,HttpSession session)throws Exception {  
		return String.valueOf(System.currentTimeMillis());
    } 

}
