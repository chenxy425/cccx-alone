package com.ship.cccx.service;

import com.ship.cccx.dao.model.AccessLog;
import com.ship.cccx.dao.model.Userext;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/6/2.
 */
public interface AccessLogService {

    public AccessLog findAccessTokenByToken(String accessToken );

    public AccessLog validateAccessToken(String accessToken );

    public AccessLog generateAccessToken( String signature, String timestamp, String deviceToken, String system, String version );

    public AccessLog updateAccessTokenByLoginUser( String accessToken, long userId, String username, Date logindate);

    public AccessLog updateAccessTokenByAccessLog( AccessLog accessLog );

    public List<AccessLog> findByLoginuserId(long loginuserId );

    public void login( long loginuserId, AccessLog accessLog );

    public void logout( Long userId, String accessToken );

    public void saveUserext( Userext userext );

    public Userext findUserext( long uid );

    public void delete( AccessLog accessLog );
}
