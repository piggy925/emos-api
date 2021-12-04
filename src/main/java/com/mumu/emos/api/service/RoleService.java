package com.mumu.emos.api.service;

import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.pojo.Role;

import java.util.ArrayList;
import java.util.HashMap;

public interface RoleService {
    ArrayList<HashMap> searchAllRole();

    PageUtils searchRoleByPage(HashMap param);

    int insert(Role role);

    ArrayList<Integer> searchUserIdByRoleId(Integer id);

    int update(Role role);

    HashMap searchById(int id);
}