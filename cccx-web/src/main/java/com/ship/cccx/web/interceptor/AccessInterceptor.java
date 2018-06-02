/**
 * 
 */
package com.ship.cccx.web.interceptor;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.ship.cccx.dao.model.AccessLog;
import com.ship.cccx.service.AccessLogService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * @author kevin
 */
public class AccessInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(AccessInterceptor.class);
	
	private static ObjectMapper objectMapper = new ObjectMapper( );
	static {
		objectMapper.configure( DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false );
	}

	@Autowired
	protected AccessLogService userService;

	@Override
	public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {
		boolean validate = false;
		String accessToken = request.getParameter( "access_token" );
		AccessLog accessLog = userService.validateAccessToken( accessToken );
		if ( accessLog != null ) {

			if ( accessLog.getStatus( ) == AccessLog.STATUS_KICKOFF ) {
				response.setStatus( HttpServletResponse.SC_GONE );
				HashMap<String, Object> ret = new HashMap<String, Object>( );
				ret.put( "username", accessLog.getLoginuser( ) );
				ret.put( "userid", accessLog.getLoginuserId( ) );
				ret.put( "kickofftime", accessLog.getKickofftime( ).getTime( ) );
				objectMapper.writeValue( response.getOutputStream( ), ret );
				response.flushBuffer( );
				userService.delete( accessLog );
				logger.info("用户[" + accessLog.getLoginuser() 
						+ "]已在其他机器登录，accesstoken[" + accessToken + "]已失效。");
				return false;
			}

			Calendar cal1 = Calendar.getInstance( );
			Calendar cal2 = Calendar.getInstance( );
			cal1.setTime( new Date( ) );
			cal2.setTime( accessLog.getCreattime( ) );
			cal2.add( Calendar.SECOND, accessLog.getExpiresIn( ) );
			if ( cal2.after( cal1 ) ) {
				logger.info("accesstoken[" + accessToken + "]链接成功");
				validate = true;
			}
			String uidstr = request.getParameter( "uid" );
			if ( StringUtils.isNotBlank( uidstr ) ) {
				long uid = NumberUtils.toLong( uidstr, 0 );
				if ( accessLog.getLoginuserId( ) != uid ) {
					logger.info("loginuserId[" + accessLog.getLoginuserId() 
							+ "] != uid[" + uid);
					validate = false;
				}
			}
		}

		if ( !validate && accessToken != null && accessToken.equals( "1352467890bqfy" ) ) {
			// 特别为了测试
			validate = true;
		}

		if ( !validate )
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		return validate;

	}

	@Override
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView )
			throws Exception {

	}

	@Override
	public void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex ) throws Exception {

	}

}
