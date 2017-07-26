package com.dinner.service.cache;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtil {
	private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    private static Properties props;
    static{
        loadProps();
    }

    synchronized static private void loadProps(){
        logger.info("开始加载properties文件内容.......");
        props = new Properties();
        InputStreamReader in = null;
        try {
        	// <!--第一种，通过类加载器进行获取properties文件流-->
            in = new InputStreamReader(PropertyUtil.class.getClassLoader().getResourceAsStream("bundle/zh-cn.properties"), "utf-8");
            //  <!--第二种，通过类进行获取properties文件流-->
            //in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
            props.load(in);
            in = new InputStreamReader(PropertyUtil.class.getClassLoader().getResourceAsStream("bundle/en.properties"), "utf-8");
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("properties文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("properties文件流关闭出现异常");
            }
        }
        logger.info("加载properties文件内容完成...........");
        logger.info("properties文件内容：" + props);
    }

    public static String getProperty(String key){
        if(null == props) {
            loadProps();
        }
        String allKey = ThreadLocalService.getPrefix() + key;
        String message = props.getProperty(allKey);
        if(message == null) return key;
        return message;
    }
    
    public static String getProperty(String key, Object[] params) {
        if(null == props) {
            loadProps();
        }
        String allKey = ThreadLocalService.getPrefix() + key;
        String message = props.getProperty(allKey);
        if(message == null) return key;
        return MessageFormat.format(message,params);
    }
    
	public static void main(String[] args) {
		System.out.println(PropertyUtil.getProperty("login.message2"));
		System.out.println(PropertyUtil.getProperty("login.message2"));
		System.out.println(PropertyUtil.getProperty("login.message", new Object[]{"ss"}));
		System.out.println(PropertyUtil.getProperty("login.message", new Object[]{"ss"}));
		System.out.println(PropertyUtil.getProperty("login.message4"));
		System.out.println(PropertyUtil.getProperty("login.message4"));
		System.out.println(PropertyUtil.getProperty("login.message3", new Object[]{"ss","ddd"}));
		System.out.println(PropertyUtil.getProperty("login.message", new Object[]{"ss2","ddd"}));
	}

}
