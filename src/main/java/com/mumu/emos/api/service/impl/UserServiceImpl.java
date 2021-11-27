package com.mumu.emos.api.service.impl;

import com.mumu.emos.api.db.dao.UserMapper;
import com.mumu.emos.api.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public Integer login(HashMap param) {
        return userMapper.login(param);
    }

    @Override
    public Set<String> searchUserPermissions(int userId) {
        return userMapper.searchUserPermissions(userId);
    }
}