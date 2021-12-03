package com.mumu.emos.api.service.impl;

import com.mumu.emos.api.db.dao.PermissionMapper;
import com.mumu.emos.api.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public ArrayList<HashMap> searchAllPermission() {
        return permissionMapper.searchAllPermission();
    }
}