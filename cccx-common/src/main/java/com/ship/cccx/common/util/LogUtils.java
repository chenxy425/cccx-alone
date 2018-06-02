/**
 * 
 */
package com.ship.cccx.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author kevin
 *
 */
public class LogUtils {

	public static void appendLog( StringBuffer sb, String field ) {
		sb.append( " " );
		if ( StringUtils.isBlank( field ) )
			sb.append( "-" );
		else
			sb.append( field );
	}
	
}
