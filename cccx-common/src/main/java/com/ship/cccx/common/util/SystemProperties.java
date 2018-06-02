package com.ship.cccx.common.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SystemProperties {
	
	/**
	 * 获取配置参数
	 * @param key
	 * @return
	 */
	public static String getSMSString(String key){
		Properties props = new Properties();
		try {
			String pathString = SystemProperties.class.getResource("/").getPath();
			if("/".equals(File.separator)){   
				pathString = pathString.replace("\\",File.separator);
			}
			pathString = pathString +"config.properties";
			pathString =  pathString.replace("%20"," ");
			FileInputStream inputStream=new FileInputStream(pathString);
			props.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value = props.getProperty(key);
		
		if (value == null) {
			return "";
		}
		return value;
	}

	public static String getMGW7165String(String key) {
		Properties props = new Properties();
		try {
			String pathString = SystemProperties.class.getResource("/").getPath();
			if("/".equals(File.separator)){   
				pathString = pathString.replace("\\",File.separator);
			}
			pathString = pathString +"mgw7165.properties";
			pathString =  pathString.replace("%20"," ");
			FileInputStream inputStream=new FileInputStream(pathString);
			props.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value = props.getProperty(key);
		
		if (value == null) {
			return "";
		}
		return value;
	}
	
	public static String getCodeString(String key) {
		Properties props = new Properties();
		try {
			String pathString = SystemProperties.class.getResource("/").getPath();
			if("/".equals(File.separator)){   
				pathString = pathString.replace("\\",File.separator);
			}
			pathString = pathString +"code.properties";
			pathString =  pathString.replace("%20"," ");
			FileInputStream inputStream=new FileInputStream(pathString);
			props.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value = props.getProperty(key);
		
		if (value == null) {
			return "";
		}
		return value;
	}
	
	public static String getIpAddr(HttpServletRequest request) { 
    	if (request == null) return "";
    	
        String ip = request.getHeader("X-Forwarded-For"); 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_CLIENT_IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        } 
        if (StringUtils.isNotEmpty(ip)) {
        	String arry[] = ip.split(",");
     		if (arry.length>0) {
     			ip = arry[0];
     		}
        }
        return ip; 
    }
	
	public static String geExpressContextString(String key) {
		Properties props = new Properties();
		try {
			String pathString = SystemProperties.class.getResource("/").getPath();
			if("/".equals(File.separator)){   
				pathString = pathString.replace("\\",File.separator);
			}
			pathString = pathString +"express-context.properties";
			pathString =  pathString.replace("%20"," ");
			FileInputStream inputStream=new FileInputStream(pathString);
			props.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value = props.getProperty(key);
		
		if (value == null) {
			return "";
		}
		return value;
	}

	public static String getValueByPropertiesFile(String key, String filename) {
		Properties props = new Properties();
		try {
			String pathString = SystemProperties.class.getResource("/").getPath();
			if("/".equals(File.separator)){   
				pathString = pathString.replace("\\",File.separator);
			}
			pathString = pathString + filename;
			pathString =  pathString.replace("%20"," ");
			FileInputStream inputStream=new FileInputStream(pathString);
			props.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value = props.getProperty(key);
		
		if (value == null) {
			return "";
		}
		return value;
	}
}
