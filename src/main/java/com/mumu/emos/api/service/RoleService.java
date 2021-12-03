package com.mumu.emos.api.service;

import com.mumu.emos.api.common.util.PageUtils;

import java.util.ArrayList;
import java.util.HashMap;

public interface RoleService {
    ArrayList<HashMap> searchAllRole();

    PageUtils searchRoleByPage(HashMap param);
}