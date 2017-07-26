package com.dinner.controller.user;


import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dinner.controller.BaseController;


@Controller
public class LoginController extends BaseController {
    
//    @Autowired
//    private UserEnterpriseService userEnterpriseService;
//
//    @Value("${cs.web.storage.server.url}")
//	private String storageServer;
//    
//	/**
//	 * @param loginName
//	 * @param password
//	 * @param captcha
//	 * @param session
//	 * @return code: -1: 验证码错误
//	 * 				 -2: 用户名密码错误
//	 * 				 -3: 系统错误
//	 * 				  0: 正确且只有一个公司
//	 *  			  1: 未注册公司，跳转到补充资料页面
//	 *  			  2: 已注册公司，且有多个，选择公司页面
//	 * 
//	 */
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//	@ResponseBody
//    public String login(@RequestParam(value="loginName", required=true) String loginName,
//    		@RequestParam(value="password", required=true) String password, 
//    		@RequestParam(value="captcha", required=true) String captcha,
//    		HttpSession session) {
//    	
//    	Long currentTime = System.currentTimeMillis();
//    	System.out.println("login time init is: " + currentTime);
//		WebuiLoginVo retVo = new WebuiLoginVo();
//		try {
//			if(!compareCaptcha(session, captcha)) {
//				retVo.setCode(-1); //验证码错误
//				retVo.setMsg(PropertyUtil.getProperty("logincontroller.error.captcha"));
//				generateAuditLog(AuditLogService.MODULE_LOGIN, AuditLogService.ACTION_LOGIN, 
//						AuditLogService.RESULT_FAILED, null, null, loginName+ "验证码错误", session);
//			} else {
//				UserVo userVo = userEnterpriseService.getUserByLoginNameAndPassword(loginName, password);
//				if(userVo == null) {
//					retVo.setCode(-2); //用户不存在
//					retVo.setMsg(PropertyUtil.getProperty("logincontroller.error.usernamepassword")); 
//					generateAuditLog(AuditLogService.MODULE_LOGIN, AuditLogService.ACTION_LOGIN, 
//							AuditLogService.RESULT_FAILED, null, null, loginName+ "用户名密码错误", session);
//					return convertReturn(retVo);
//				} else if(!StringUtils.isBlank(userVo.getErrorMsg())) {
//					retVo.setCode(-3); //企业不存在或冻结
//					retVo.setMsg(userVo.getErrorMsg()); 
//					generateAuditLog(AuditLogService.MODULE_LOGIN, AuditLogService.ACTION_LOGIN, 
//							AuditLogService.RESULT_FAILED, null, null, loginName+ userVo.getErrorMsg(), session);
//					return convertReturn(retVo);
//				} else if (userVo.getBlnAdmin() != Constants.DTO_USER_ADMIN_TRUE && userVo.getBlnAdmin() != Constants.DTO_USER_ADMIN_SUBADMIN) {
//					retVo.setCode(-4); //普通用户不能登录管理后台
//					retVo.setMsg(PropertyUtil.getProperty("logincontroller.error.cannotlogin")); 
//					generateAuditLog(AuditLogService.MODULE_LOGIN, AuditLogService.ACTION_LOGIN, 
//							AuditLogService.RESULT_FAILED, null, null, loginName+ "您不能登录管理后台", session);
//					return convertReturn(retVo);
//				} else {
//					if(userVo.getFirstFlag() == Constants.DTO_USER_FIRST_FLAG_UNLOGINED) {
//						userEnterpriseService.updateUserFirstFlag(userVo.getId());
//					}
//					getCacheService().setValue(session,Constants.USER_CONTEXT, userVo);
//					retVo.setUserVo(new WebuiUserVo());
//					retVo.getUserVo().setId(userVo.getId());
//					retVo.getUserVo().setBlnAdmin(userVo.getBlnAdmin());
//					retVo.getUserVo().setFirstFlag(userVo.getFirstFlag());
//					retVo.getUserVo().setName(userVo.getName());
//					retVo.getUserVo().setEnterpriseId(userVo.getEnterpriseId());
//					retVo.getUserVo().setEnterpriseName(userVo.getEnterpriseVo()==null? null : userVo.getEnterpriseVo().getEnterpriseName());
//                    retVo.getUserVo().setRemainDays(userVo.getEnterpriseVo().getRemainDays());
//					retVo.getUserVo().setNeedRemindExpire(userVo.getEnterpriseVo().isNeedRemindExpire());
//                    retVo.getUserVo().setIcon(userVo.getIcon());
//					retVo.getUserVo().setOrgId(userVo.getOrganizationVo()==null? null: userVo.getOrganizationVo().getId());
//					retVo.getUserVo().setOrgName(userVo.getOrganizationVo()==null? null: userVo.getOrganizationVo().getOrgName());
//					retVo.getUserVo().setIconServer(storageServer);
//					generateAuditLog(AuditLogService.MODULE_LOGIN, AuditLogService.ACTION_LOGIN, 
//							AuditLogService.RESULT_SUCCESS, null, null, loginName, session);
//				}
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//			retVo.setCode(-4); //系统错误
//			retVo.setMsg(PropertyUtil.getProperty("logincontroller.error.system"));
//			generateAuditLog(AuditLogService.MODULE_LOGIN, AuditLogService.ACTION_LOGIN, 
//					AuditLogService.RESULT_FAILED, null, null, loginName+ "系统错误", session);
//		}
//		getCacheService().removeValue(session, Constants.USER_CAPTCHA);
//		if(retVo.getCode()>=0) {
//			String uuid =  UUID.randomUUID().toString();
//			retVo.setToken(uuid);
//			getCacheService().setValue(session,Constants.USER_TOKEN, uuid);
//		}
//		System.out.println("login time init time is: " + currentTime + " and cost " + (System.currentTimeMillis() - currentTime));
//		return convertReturn(retVo);
//    }

}
