package com.mumu.emos.api.db.dao;

import com.mumu.emos.api.db.pojo.User;

import java.util.ArrayList;
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

    HashMap searchUserInfo(int userId);

    Integer searchDeptManagerId(int id);

    Integer searchGmId();

    int insert(User user);

    int update(HashMap param);

    HashMap searchById(int userId);

    int deleteUserByIds(Integer[] ids);

    ArrayList<HashMap> searchAllUser();

    ArrayList<String> searchUserRoles(int userId);

    HashMap searchNameAndDept(int userId);
}