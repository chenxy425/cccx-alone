package com.ship.cccx.common.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MessUtils {
	
	public static boolean isCorrectUsername(String username){
		boolean isCorrect = true;
		
		return isCorrect;
	}
	
	public static boolean isCorrectPassword(String password){
		
		boolean isCorrect = false;
		
		if(password!=null && password.length()>=6){
			 boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
			 boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
			 String str = password;
			 for(int i=0 ; i<str.length() ; i++){ //循环遍历字符串   
				 if(Character.isDigit(str.charAt(i))){     //用char包装类中的判断数字的方法判断每一个字符
				     isDigit = true;   
				 }
				 if(Character.isLetter(str.charAt(i))){   //用char包装类中的判断字母的方法判断每一个字符
				         isLetter = true;
				 }
			 }	
			 if(isDigit && isLetter)
				 isCorrect = true;
		}
		
		return isCorrect;
		
	}
	
    public static String getTomcatWebappsPath(HttpServletRequest request){
    	String tomcatRoot = request.getSession().getServletContext().getRealPath("/");  
    	        String[] foo = tomcatRoot.split("/");  
    	        StringBuilder tomcatWebAppsBuilder = new StringBuilder();  
    	        int i = 0;  
    	        for(String paths : foo){  
    	            ++i;  
    	            if(i != foo.length){  
    	                tomcatWebAppsBuilder.append(paths);  
    	                tomcatWebAppsBuilder.append("/");  
    	            }  
    	        }  
    	        String tomcatWebApps = tomcatWebAppsBuilder.toString();
    	        return tomcatWebApps;
    }
    
    /**
     * 根据身份证号码获取年龄
     * 
     * @param IDCardNum
     * @return
     */
    public static int getAgeByIDCard (String IDCardNum) {
		int year = 0, month = 0, day = 0, idLength = IDCardNum.length();
    	Calendar cal1 = Calendar.getInstance();
    	Calendar today = Calendar.getInstance();
    	if(idLength == 18) {
	    	year = Integer.parseInt(IDCardNum.substring(6,10));
	    	month = Integer.parseInt(IDCardNum.substring(10,12));
	    	day = Integer.parseInt(IDCardNum.substring(12,14));
    	} else if(idLength == 15) {
	    	year = Integer.parseInt(IDCardNum.substring(6,8)) + 1900;
	    	month = Integer.parseInt(IDCardNum.substring(8,10));
	    	day = Integer.parseInt(IDCardNum.substring(10,12));
    	}
    	cal1.set(year, month, day);
    	int m = (today.get(today.MONTH)) - (cal1.get(cal1.MONTH));
		int y = (today.get(today.YEAR)) - (cal1.get(cal1.YEAR));
		return (y * 12 + m)/12;
	}
    
    /**
	 * 将字符串日期从一种格式转化成另一种格式
	 * 
	 * @param dateStr
	 * @param fromPattern
	 * @param toPattern
	 * @return
	 * @throws ParseException
	 */
	public static String dateFormat (Date date, String pattern) {
		SimpleDateFormat sdf = new  SimpleDateFormat(pattern);  
		return sdf.format(date);
	}
	
	/**
	 * 将字符串日期从一种格式转化成另一种格式
	 * 
	 * @param dateStr
	 * @param fromPattern
	 * @param toPattern
	 * @return
	 * @throws ParseException
	 */
	public static String getCurrentChinaDateFormat() {
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyyMMddHHmmssSSS");  
		return sdf.format(new Date());
	}
	
	public static boolean compareVersion (String currentStr, String compareStr) {
		if (StringUtils.isEmpty(currentStr)) {
			return false;
		}
		String _currentStr = currentStr.replaceAll("\\.", "");
		String _compareStr = compareStr.replaceAll("\\.", "");
		if (_currentStr.length() != _compareStr.length()) {
			//去掉.后长度不一致，则将位数少的后面补0
			int currentLen = _currentStr.length();
			int compareLen = _compareStr.length();
			if (currentLen < compareLen) {
				//把currentStr补0
				for (int i=0; i<(compareLen-currentLen); i++) {
					_currentStr+="0";
				}
			} else {
				//把_compareStr补0
				for (int i=0; i<(currentLen-compareLen); i++) {
					_compareStr+="0";
				}
			}
		}
		int _currentVal = Integer.parseInt(_currentStr);
		int _compareVal = Integer.parseInt(_compareStr);
		if (_currentVal >= _compareVal) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		//System.out.println(dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
		//System.out.println(getCurrentChinaDateFormat());
		System.out.println(compareVersion("1.2.0", "1.2.0"));
	}
}
