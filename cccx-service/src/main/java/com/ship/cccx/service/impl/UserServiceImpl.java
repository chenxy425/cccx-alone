package com.ship.cccx.service.impl;

import com.ship.cccx.dao.mapper.UserMapper;
import com.ship.cccx.dao.model.UserExample;
import com.ship.cccx.service.UserService;
import com.ship.cccx.common.annotation.BaseService;
import com.ship.cccx.common.base.BaseServiceImpl;
import com.ship.cccx.dao.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UserService实现
* Created by thinkam on 17-10-31.
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User, UserExample> implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

}