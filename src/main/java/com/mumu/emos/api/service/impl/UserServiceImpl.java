package com.mumu.emos.api.service.impl;

import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.dao.UserMapper;
import com.mumu.emos.api.db.pojo.User;
import com.mumu.emos.api.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public HashMap searchUserSummary(int userId) {
        return userMapper.searchUserSummary(userId);
    }

    @Override
    public int updatePassword(HashMap param) {
        return userMapper.updatePassword(param);
    }

    @Override
    public Integer validatePassword(HashMap param) {
        return userMapper.validatePassword(param);
    }

    @Override
    public PageUtils searchUserByPage(HashMap param) {
        List<HashMap> list = userMapper.searchUserByPage(param);
        long count = userMapper.searchUserCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        return new PageUtils(list, count, start, length);
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int update(HashMap param) {
        return userMapper.update(param);
    }

    @Override
    public HashMap searchById(int userId) {
        return userMapper.searchById(userId);
    }
}