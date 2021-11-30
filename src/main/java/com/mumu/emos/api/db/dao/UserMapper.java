package com.mumu.emos.api.db.dao;

import com.mumu.emos.api.db.pojo.User;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface UserMapper {
    Set<String> searchUserPermissions(int userId);

    Integer login(HashMap param);

    HashMap searchUserSummary(int userId);

    int updatePassword(HashMap param);

    Integer validatePassword(HashMap param);

    List<HashMap> searchUserByPage(HashMap param);

    long searchUserCount(HashMap param);

    int insert(User user);
}