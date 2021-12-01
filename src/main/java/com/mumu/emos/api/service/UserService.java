package com.mumu.emos.api.service;

import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.pojo.User;

import java.util.HashMap;
import java.util.Set;

public interface UserService {
    Integer login(HashMap param);

    Set<String> searchUserPermissions(int userId);

    HashMap searchUserSummary(int userId);

    int updatePassword(HashMap param);

    Integer validatePassword(HashMap param);

    PageUtils searchUserByPage(HashMap param);

    int insert(User user);

    int update(HashMap param);

    HashMap searchById(int userId);
}