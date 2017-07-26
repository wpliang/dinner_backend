package com.dinner.service.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadLocalService {

	public static final String LANGUAGE_CN="zh-cn";
	private static ThreadLocal<String> language = new ThreadLocal<String>(){
        @Override 
        protected String initialValue() {
            return LANGUAGE_CN;
        }
	};

	private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
	
	public static void setLanguage(String lang) {
		if(lang == null || lang.isEmpty()) {
			language.set(LANGUAGE_CN);
		} else {
			language.set(lang);
		}
	}
	
	public static String getLanguage() {
		String lang =  language.get();
		if(lang == null || lang.isEmpty()) {
			return LANGUAGE_CN;
		} else {
			return lang;
		}
	}
 
	public static String getPrefix() {
		String lang =  language.get();
		Thread t = Thread.currentThread();
		logger.error("getprefix thread id:" + t.getId() + " and language is:" + lang ==null? "zhcn." : (lang + "."));
		if(lang == null || lang.isEmpty()) {
			return "zhcn.";
		} else if(lang.equalsIgnoreCase("en")) {
			return "en.";
		} else {
			return "zhcn."; 
		}
	}
}
