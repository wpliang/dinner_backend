package com.dinner.controller.interceptor;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dinner.common.base.constant.Constants;
import com.dinner.controller.cache.CacheService;
import com.dinner.db.vo.user.UserVo;
import com.dinner.service.cache.ThreadLocalService;

import lombok.Setter;

public class ControllerInterceptor implements HandlerInterceptor {
	
	@Setter
	private List<String> excludedUrls;
	
	@Setter
	private CacheService cacheService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestUri = request.getRequestURI();
		ThreadLocalService.setLanguage(ThreadLocalService.LANGUAGE_CN);
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length>0) {
        	for(Cookie oneCookie: cookies) {
        		if(oneCookie.getName().equalsIgnoreCase("lang")) {
        			ThreadLocalService.setLanguage(oneCookie.getValue());
        			break;
        		}
        	}
        }
        for (String url : excludedUrls) {
            if (requestUri.contains(url)) {
                return true;
            }
        }
        if(requestUri.equals("/")) {
        	 return true;
        }

        UserVo userVo = cacheService.getCacheUserVo(request.getSession());
        if (userVo == null) {
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "用户未认证。");
        	return false;
        }

        String token = cacheService.getStringValue(request.getSession(),Constants.USER_TOKEN);
        String reqToken = request.getHeader("token");
        if(StringUtils.isBlank(reqToken)) {
	        cookies = request.getCookies();
	        if(cookies!=null && cookies.length>0) {
	        	for(Cookie oneCookie: cookies) {
	        		if(oneCookie.getName().equalsIgnoreCase("token")) {
	        			reqToken = oneCookie.getValue();
	        			break;
	        		}
	        	}
	        }
        }
        
        if(reqToken==null || token ==null || !token.equals(reqToken)) {
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "用户未认证。");
        	return false;
        }
        
        return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	
}
