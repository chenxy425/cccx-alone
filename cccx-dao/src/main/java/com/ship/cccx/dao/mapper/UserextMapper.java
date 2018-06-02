package com.ship.cccx.dao.mapper;

import com.ship.cccx.dao.model.Userext;

/**
 * Created by Administrator on 2018/6/2.
 */
public interface UserextMapper {

    public Userext selectByPrimaryKey(long uid);

    public void insert(Userext userext);
}
