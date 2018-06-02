package com.ship.cccx.common.util;

public class RandomUtils {

	private static final String charlist = "0123456789";

	public static final int inc = 1000;

	public static final int len = 16;

	/**
	 * random string, but it is not unique
	 * 
	 * @param len
	 *            random string length
	 * @return random string
	 */
	public static String createRandomString( int len ) {

		String str = new String( );
		for ( int i = 0; i < len; i++ )
			str += charlist.charAt( getRandom( charlist.length( ) ) );
		return str;
	}

	/**
	 * random int, scope: 0 ~ (mod-1)
	 * 
	 * @param mod
	 *            scope
	 * @return
	 */
	public static int getRandom( int mod ) {
		if ( mod < 1 )
			return 0;
		int ret = getInt( ) % mod;
		return ret;
	}

	private static int getInt( ) {
		int ret = Math.abs( Long.valueOf( getRandomNumString( ) ).intValue( ) );
		return ret;
	}

	private static String getRandomNumString( ) {
		double d = Math.random( );

		String dStr = String.valueOf( d ).replaceAll( "[^\\d]", "" );
		if ( dStr.length( ) > 1 )
			dStr = dStr.substring( 0, dStr.length( ) - 1 );
		return dStr;
	}

}
