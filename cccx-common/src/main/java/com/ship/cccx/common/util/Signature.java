package com.ship.cccx.common.util;

/*import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.util.ByteSource;*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Signature {
	
	
	
	public static String computeSignature(String timestamp, String source, 
			String version, String imei, String sys, String width, String height){
		
		String signature = null;
		
		List<String> parameters = new ArrayList<String>();
		
		String token = Constants.token;
		parameters.add(token);
		
		if(timestamp!=null) parameters.add(timestamp);else parameters.add("");
		if(source!=null) parameters.add(source);else parameters.add("");
		if(version!=null) parameters.add(version);else parameters.add("");
		if(imei!=null) parameters.add(imei);else parameters.add("");
		if(sys!=null) parameters.add(sys);else parameters.add("");
		if(width!=null) parameters.add(width);else parameters.add("");
		if(height!=null) parameters.add(height);else parameters.add("");

		Collections.sort(parameters, new SpellCompalor());
		
		String combined = "";
		for(String p : parameters){
			combined += p;
		}
		
		System.out.println("Combined: "+ combined);
		
		/*ByteSource byteSource = new Sha1Hash(combined);
		signature = byteSource.toString();*/
		return signature;
	}
	
	public static String computeSignatureCombined(String timestamp, String source, 
			String version, String imei, String sys, String width, String height){
		
		List<String> parameters = new ArrayList<String>();
		
		String token = Constants.token;
		parameters.add(token);
		
		if(timestamp!=null) parameters.add(timestamp);else parameters.add("");
		if(source!=null) parameters.add(source);else parameters.add("");
		if(version!=null) parameters.add(version);else parameters.add("");
		if(imei!=null) parameters.add(imei);else parameters.add("");
		if(sys!=null) parameters.add(sys);else parameters.add("");
		if(width!=null) parameters.add(width);else parameters.add("");
		if(height!=null) parameters.add(height);else parameters.add("");

		Collections.sort(parameters, new SpellCompalor());
		
		String combined = "";
		for(String p : parameters){
			combined += p;
		}
		return combined;
	}
	
	
}
