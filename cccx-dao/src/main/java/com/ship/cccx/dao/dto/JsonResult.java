package com.ship.cccx.dao.dto;

import java.util.Date;

public class JsonResult {
	
	private int errorCode;
	private String errorMessage;
	private Object data;
	
	private Date currentTime;
		
	public Object getData(){
		return this.data;
	}
	
	public void setData(Object data){
		this.data = data;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Date getCurrentTime( ) {
		return currentTime;
	}

	public void setCurrentTime( Date currentTime ) {
		this.currentTime = currentTime;
	}

}
