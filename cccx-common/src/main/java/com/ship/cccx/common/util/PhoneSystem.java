package com.ship.cccx.common.util;

import javax.servlet.http.HttpServletRequest;

public class PhoneSystem {

	private static final String IOS = "ios";
	private static final String ANDROID = "android";
	
	public static String getPhoneSystemType(HttpServletRequest request) {
		String ua = request.getHeader("user-agent");
		if (ua == null) {
			ua = "";
		} else {
			ua = ua.toLowerCase();
		}
		if (ua.contains(IOS)) {
			return IOS;
		} else if (ua.contains(ANDROID)) {
			return ANDROID;
		}
		return null;
	}
}
