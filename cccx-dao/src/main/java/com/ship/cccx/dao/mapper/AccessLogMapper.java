package com.ship.cccx.dao.mapper;

import com.ship.cccx.dao.model.AccessLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/6/2.
 */
public interface AccessLogMapper {

    public AccessLog findByAccessToken(String accessToken );

    public List<AccessLog> findByLoginuserId(@Param("id") long loginuserId);

    public AccessLog insert(AccessLog entity);

    public void delete(AccessLog entity);

    public void update(AccessLog al);
}
