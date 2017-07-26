package com.dinner.controller.user;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dinner.controller.BaseController;
import com.dinner.db.vo.user.UserVo;
import com.dinner.service.user.UserEnterpriseService;

import net.sf.json.JSONObject;


@Controller
@RequestMapping(value = "/org")
public class OrganizationController extends BaseController {
    
    @Autowired
    private UserEnterpriseService userEnterpriseService;

	
	@RequestMapping(value = "/registEnterprise", method = RequestMethod.POST)
	@ResponseBody
	 public String registEnterprise(@RequestParam(value="entName", required=true) String entName, 
	    		@RequestParam(value="userName", required=true) String userName, 
	    		@RequestParam(value="email", required=true) String email, 
	    		@RequestParam(value="phone", required=false) String phone,
	    		@RequestParam(value="comment", required=false) String comment,
	    		HttpSession session) { 
			JSONObject jsonObject = new JSONObject();
			UserVo userVo = userEnterpriseService.registEnterprise(entName, userName, email, phone, comment);
			if(!StringUtils.isBlank(userVo.getErrorMsg())){
				jsonObject.put("code", -1);
				jsonObject.put("msg", userVo.getErrorMsg());
			} else {
				jsonObject.put("code", 0);
				jsonObject.put("userVo", userVo);
			}
			return jsonObject.toString();
	    }  
}
