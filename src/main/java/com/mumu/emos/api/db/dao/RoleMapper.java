package com.mumu.emos.api.db.dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface RoleMapper {
    ArrayList<HashMap> searchAllRole();

    ArrayList<HashMap> searchRoleByPage(HashMap param);

    long searchRoleCount(HashMap param);
}