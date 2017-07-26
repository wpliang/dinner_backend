package com.dinner.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.dinner.common.base.constant.Constants;
import com.dinner.common.base.utils.JsonUtil;
import com.dinner.controller.cache.CacheService;

import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONObject;


public abstract class BaseController {
    
    @Value("${cs.web.devMode:false}")
	private boolean devMode;

	@Autowired
	@Getter @Setter
	private CacheService cacheService;
	
//	@Autowired
//	@Getter @Setter
//	private AuditLogService auditLogService;
	
	protected boolean compareCaptcha(HttpSession session, String captcha) {
		if(devMode && captcha!=null && captcha.equals("1111")) {
			return true;
		}
		String sessionCaptcha = getCacheService().getStringValue(session,Constants.USER_CAPTCHA);
		if(sessionCaptcha==null || !sessionCaptcha.toString().equalsIgnoreCase(captcha)) {
			return false;
		}
		return true;
	}
	
	protected boolean compareEmailCode(HttpSession session, String emailCode) {
		String sessionEmailCode = getCacheService().getStringValue(session, Constants.USER_EMAIL_CODE);
		Object sessionEmailCodeExpire = getCacheService().getObjValue(session, Constants.USER_EMAIL_CODE_TIME_END);
		Long expireTime = (Long)sessionEmailCodeExpire;
		if(sessionEmailCodeExpire == null || sessionEmailCode==null || !sessionEmailCode.toString().equalsIgnoreCase(emailCode)) {
			return false;
		}
		if(System.currentTimeMillis() > expireTime) {
			getCacheService().removeValue(session, Constants.USER_EMAIL_CODE);
			getCacheService().removeValue(session, Constants.USER_EMAIL_CODE_TIME_END);
			return false;
		}
		getCacheService().removeValue(session, Constants.USER_EMAIL_CODE);
		getCacheService().removeValue(session, Constants.USER_EMAIL_CODE_TIME_END);
		return true;
	}
	
//	protected String convertReturn(WebuiBaseVo retVo) {
//		String retStr = null;
//		try {
//			retStr =  JsonUtil.toJsonString(retVo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if(retStr!=null) return retStr;
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("code", -101);
//		jsonObject.put("msg", "返回数据转换出错");
//		return jsonObject.toString();
//	}
	
//	protected void generateAuditLog(String module, String action, int result, Long objectId, 
//			String objectName, String comment, HttpSession session) {
//		ConsoleLogVo vo = new ConsoleLogVo();
//		UserVo userVo = cacheService.getCacheUserVo(session);
//		if(userVo!=null) {
//			vo.setEnterpriseId(userVo.getEnterpriseId());
//			vo.setEnterpriseName(userVo.getEnterpriseVo()!=null ? userVo.getEnterpriseVo().getEnterpriseName() : "");
//			vo.setUserId(userVo.getId());
//			vo.setUserName(userVo.getName());
//		}
//		vo.setModule(module);
//		vo.setAction(action);
//		vo.setResult(result);
//		vo.setObjectId(objectId);
//		vo.setObjectName(objectName);
//		vo.setComment(comment);
//		auditLogService.recordLog(vo);
//	}
}
