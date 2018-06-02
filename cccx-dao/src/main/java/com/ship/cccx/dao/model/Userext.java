/**
 * 
 */
package com.ship.cccx.dao.model;

import java.io.Serializable;

/**
 * @author kevin
 */
public class Userext implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long uid;

	private String avatar;

	public Long getUid( ) {
		return uid;
	}

	public void setUid( Long uid ) {
		this.uid = uid;
	}

	public String getAvatar( ) {
		return avatar;
	}

	public void setAvatar( String avatar ) {
		this.avatar = avatar;
	}

}
