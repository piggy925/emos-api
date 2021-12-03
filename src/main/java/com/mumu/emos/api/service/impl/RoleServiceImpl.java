package com.mumu.emos.api.service.impl;

import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.dao.RoleMapper;
import com.mumu.emos.api.db.pojo.Role;
import com.mumu.emos.api.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public ArrayList<HashMap> searchAllRole() {
        ArrayList<HashMap> list = roleMapper.searchAllRole();
        return list;
    }

    @Override
    public PageUtils searchRoleByPage(HashMap param) {
        ArrayList<HashMap> list = roleMapper.searchRoleByPage(param);
        long count = roleMapper.searchRoleCount(param);
        int start = (int) param.get("start");
        int length = (int) param.get("length");
        return new PageUtils(list, count, start, length);
    }

    @Override
    public int insert(Role role) {
        return roleMapper.insert(role);
    }
}