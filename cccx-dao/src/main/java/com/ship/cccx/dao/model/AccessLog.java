package com.ship.cccx.dao.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the accessLog database table.
 */
public class AccessLog implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final short STATUS_NORMAL = 1;

	public static final short STATUS_KICKOFF = 0;// 被踢下线

	private long id;

	private String accessToken;

	private Date creattime;

	private int expiresIn;

	private Date logintime;

	private String loginuser;

	private long loginuserId;

	private String veriMobile;

	private String vericode;

	private Date vericodeExpired;

	private String deviceToken;

	private String system;

	private String version;

	private short status = 1;

	private Date kickofftime;

	public AccessLog( ) {
	}

	public String getDeviceToken( ) {
		return deviceToken;
	}

	public void setDeviceToken( String deviceToken ) {
		this.deviceToken = deviceToken;
	}

	public String getSystem( ) {
		return system;
	}

	public void setSystem( String system ) {
		this.system = system;
	}

	public String getVersion( ) {
		return version;
	}

	public void setVersion( String version ) {
		this.version = version;
	}

	public long getId( ) {
		return this.id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	public String getAccessToken( ) {
		return this.accessToken;
	}

	public void setAccessToken( String accessToken ) {
		this.accessToken = accessToken;
	}

	public Date getCreattime( ) {
		return this.creattime;
	}

	public void setCreattime( Date creattime ) {
		this.creattime = creattime;
	}

	public int getExpiresIn( ) {
		return this.expiresIn;
	}

	public void setExpiresIn( int expiresIn ) {
		this.expiresIn = expiresIn;
	}

	public Date getLogintime( ) {
		return this.logintime;
	}

	public void setLogintime( Date logintime ) {
		this.logintime = logintime;
	}

	public String getLoginuser( ) {
		return this.loginuser;
	}

	public void setLoginuser( String loginuser ) {
		this.loginuser = loginuser;
	}

	public String getVericode( ) {
		return vericode;
	}

	public void setVericode( String vericode ) {
		this.vericode = vericode;
	}

	public Date getVericodeExpired( ) {
		return vericodeExpired;
	}

	public void setVericodeExpired( Date vericodeExpired ) {
		this.vericodeExpired = vericodeExpired;
	}

	public String getVeriMobile( ) {
		return veriMobile;
	}

	public void setVeriMobile( String veriMobile ) {
		this.veriMobile = veriMobile;
	}

	public long getLoginuserId( ) {
		return loginuserId;
	}

	public void setLoginuserId( long loginuserId ) {
		this.loginuserId = loginuserId;
	}

	public short getStatus( ) {
		return status;
	}

	public void setStatus( short status ) {
		this.status = status;
	}

	public Date getKickofftime( ) {
		return kickofftime;
	}

	public void setKickofftime( Date kickofftime ) {
		this.kickofftime = kickofftime;
	}
	
	public String toString( ) {
		return ReflectionToStringBuilder.toString( this );
	}

}