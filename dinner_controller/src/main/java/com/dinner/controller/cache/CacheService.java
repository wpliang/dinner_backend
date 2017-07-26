package com.dinner.controller.cache;

import javax.servlet.http.HttpSession;

import com.dinner.db.vo.user.UserVo;

public interface CacheService {
	public void setValue(HttpSession session, String key, Object value);
	public String getStringValue(HttpSession session, String key);
	public Object getObjValue(HttpSession session, String key);
	public UserVo getCacheUserVo(HttpSession session);
	public Long getCacheUserEntId(HttpSession session);
	public void removeValue(HttpSession session, String key);
	
	
}
