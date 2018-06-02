package com.ship.cccx.dao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AccessToken", description = "app token信息")
public class AccessToken {

	@ApiModelProperty(notes = "获取的token")
	private String access_token;
	@ApiModelProperty(notes = "有效期")
	private long expires_in;
	
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public long getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}
	
	

}
