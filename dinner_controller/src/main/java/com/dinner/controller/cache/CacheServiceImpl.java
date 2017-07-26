package com.dinner.controller.cache;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.dinner.common.base.constant.Constants;
import com.dinner.db.vo.user.UserVo;

@Service("cacheService")
public class CacheServiceImpl implements CacheService {

	public void setValue(HttpSession session, String key, Object value) {
		if(session == null || StringUtils.isBlank(key) || value == null) {
			return ;
		}
		session.setAttribute(key,value);
	}
	
	public String getStringValue(HttpSession session, String key) {
		if(session == null || StringUtils.isBlank(key)) {
			return null;
		}
		Object sessionValue = session.getAttribute(key);
		if(sessionValue == null) return null;
		return sessionValue.toString();
	}
	
	public Object getObjValue(HttpSession session, String key) {
		if(session == null || StringUtils.isBlank(key)) {
			return null;
		}
		return session.getAttribute(key);
	}
	
	public UserVo getCacheUserVo(HttpSession session) {
		Object sessionUser = session.getAttribute(Constants.USER_CONTEXT); 
		if(sessionUser == null) return null;
		return (UserVo)sessionUser;
	}
	
	public Long getCacheUserEntId(HttpSession session) {
		UserVo userVo = getCacheUserVo(session);
		if(userVo == null) return null;
		return userVo.getEnterpriseId();
	}
	
	public void removeValue(HttpSession session, String key) {
		if(session == null || StringUtils.isBlank(key)) {
			return;
		}
		session.removeAttribute(key);
	}
}
