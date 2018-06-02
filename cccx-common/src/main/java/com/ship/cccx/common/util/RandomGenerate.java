package com.ship.cccx.common.util;

import java.util.Random;

public class RandomGenerate {
	
	public static String generateCode(int length){
		
		Random random = new Random();
		
		String verCode = "";
		
		for(int i=0; i<length; i++){
			verCode += random.nextInt(i+1);
		}

		return verCode;
	}
	
	public static String generateUniqueCode(){
		
		Random random = new Random();
		
		String verCode = "";
		
		for(int i=0; i<20; i++){
			verCode += random.nextInt(i+1);
		}

		return verCode;
	}
	
	public static boolean sholdGetbonus(int percent, int total){
		Random random = new Random();
		int num  = 1+ random.nextInt(total);
		if(num<=percent)
			return true;
		else
			return false;
	}

}
