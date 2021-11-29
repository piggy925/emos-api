package com.mumu.emos.api.service.impl;

import com.mumu.emos.api.db.dao.RoleMapper;
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
}