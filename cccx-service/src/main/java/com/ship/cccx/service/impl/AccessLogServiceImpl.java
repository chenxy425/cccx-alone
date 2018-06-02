package com.ship.cccx.service.impl;

import com.ship.cccx.common.util.LogUtils;
import com.ship.cccx.dao.model.AccessLog;
import com.ship.cccx.dao.model.Userext;
/*import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.util.ByteSource;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ship.cccx.common.util.RandomGenerate;
import com.ship.cccx.dao.mapper.AccessLogMapper;
import com.ship.cccx.dao.mapper.UserextMapper;
import com.ship.cccx.service.AccessLogService;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2018/6/2.
 */
@Service("accessLogService")
public class AccessLogServiceImpl implements AccessLogService {

    private static final int expires = 604800;

    @Autowired
    private AccessLogMapper accessLogRepository;

    @Autowired
    private UserextMapper userextRepository;

    @Override
    public AccessLog validateAccessToken(String accessToken ) {

        if ( accessToken != null )
            return accessLogRepository.findByAccessToken( accessToken );

        return null;
    }

    @Override
    public AccessLog generateAccessToken( String signature, String timestamp, String deviceToken, String system, String version ) {
        String combinded = signature + timestamp;
        String salt = RandomGenerate.generateUniqueCode( );
        /*ByteSource byteSource = new Sha1Hash( combinded, salt );
        String accessToken = byteSource.toString( );*/
        String accessToken = combinded + salt;

                AccessLog accessLog = new AccessLog( );
        accessLog.setAccessToken( accessToken );
        accessLog.setExpiresIn( expires );
        accessLog.setCreattime( new Date( ) );
        accessLog.setDeviceToken( deviceToken );
        accessLog.setSystem( system );
        accessLog.setVersion( version );
        accessLogRepository.insert( accessLog );

        return accessLog;

    }

    @Override
    public AccessLog updateAccessTokenByLoginUser( String accessToken, long userId, String username, Date logindate ) {

        AccessLog accessLog = accessLogRepository.findByAccessToken( accessToken );
        if ( accessLog != null ) {
            accessLog.setLoginuserId( userId );
            accessLog.setLoginuser( username );
            accessLog.setLogintime( logindate );
            accessLog.setStatus(AccessLog.STATUS_NORMAL);
            accessLogRepository.update( accessLog );
        }

        return accessLog;
    }

    @Override
    public AccessLog findAccessTokenByToken( String accessToken ) {
        return accessLogRepository.findByAccessToken( accessToken );
    }

    @Override
    public AccessLog updateAccessTokenByAccessLog( AccessLog accessLog ) {
        accessLogRepository.update( accessLog );
        return accessLog;
    }

    @Override
    public List<AccessLog> findByLoginuserId( long loginuserId ) {
        return accessLogRepository.findByLoginuserId( loginuserId );
    }

    private Logger nlog = Logger.getLogger( "umeng_notice" );

    @Override
    public void login( long loginuserId, AccessLog accessLog ) {
        List<AccessLog> list = this.findByLoginuserId( loginuserId );
        for ( AccessLog al : list ) {
            if ( al.getAccessToken().equals( accessLog.getAccessToken()))
                continue;
            if ( accessLog.getDeviceToken() != null && al.getDeviceToken() != null
                    && !accessLog.getDeviceToken().trim().equals( al.getDeviceToken().trim( ) ) ) {

                StringBuffer sb = new StringBuffer( );
                LogUtils.appendLog( sb, accessLog.getDeviceToken( ) );
                LogUtils.appendLog( sb, accessLog.getSystem( ) );
                LogUtils.appendLog( sb, al.getDeviceToken( ) );
                LogUtils.appendLog( sb, accessLog.getLoginuser( ) );
                LogUtils.appendLog( sb, al.getSystem( ) );
                nlog.info( sb.toString( ) );
                al.setStatus( AccessLog.STATUS_KICKOFF );
                al.setKickofftime( new Date( ) );
                accessLogRepository.update( al );

            } else {
                accessLogRepository.delete( al );
            }

        }
    }

    @Override
    public void logout( Long userId, String accessToken ) {

        AccessLog accessLog = accessLogRepository.findByAccessToken( accessToken );
        if ( accessLog != null && userId != null && accessLog.getLoginuserId( ) == userId ) {
            accessLogRepository.delete( accessLog );
        }

    }

    @Override
    public void delete( AccessLog accessLog ) {
        accessLogRepository.delete( accessLog );
    }

    @Override
    public void saveUserext( Userext userext ) {
        userextRepository.insert(userext);
    }

    @Override
    public Userext findUserext( long uid ) {
        return userextRepository.selectByPrimaryKey( uid );
    }
}
