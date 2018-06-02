package com.ship.cccx.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SMS {

	public static final String SMS_CONTENT = "亲，您的手机动态验证码为{code}。为保证账户安全请勿泄漏他人。";

	public static String sendMessage( Class<?> smsClass, String smsContent, String... mobiles ) {
		try {
			/*WebClient client = WebClient.create( Constants.SMS_SEND_URL );
			LogRsc logrsc = new LogRsc( );
			logrsc.setSource( "P2P - " + new Date( ).getTime( ) );
			logrsc.setUserName( "XXX" );
			String mobilestr = "";
			for ( int i = 0; i < mobiles.length; i++ ) {
				if ( i == mobiles.length - 1 )
					mobilestr += mobiles[i];
				else
					mobilestr += mobiles[i] + ",";
			}
			logrsc.setMobile( mobilestr );
			logrsc.setContent( smsContent );
			logrsc.setSendTime( new Date( ) );
			logrsc.setClazz( smsClass );
			ParamsVo params = new ParamsVo( );
			params.setMobiles( mobiles );
			params.setContent( smsContent );
			params.setLog( logrsc );
			String str = client.path( "sms/smsSend" ).accept( "application/xml" ).post( params, String.class );
			return str;*/
			return null;
		} catch ( Exception e ) {
			e.printStackTrace( );
			return null;
		}
	}

}
