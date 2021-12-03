package com.mumu.emos.api.db.dao;

import com.mumu.emos.api.db.pojo.Role;

import java.util.ArrayList;
import java.util.HashMap;

public interface RoleMapper {
    ArrayList<HashMap> searchAllRole();

    ArrayList<HashMap> searchRoleByPage(HashMap param);

    long searchRoleCount(HashMap param);

    int insert(Role role);
}