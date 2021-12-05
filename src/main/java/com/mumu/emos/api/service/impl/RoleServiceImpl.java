package com.mumu.emos.api.service.impl;

import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.dao.RoleMapper;
import com.mumu.emos.api.db.pojo.Role;
import com.mumu.emos.api.exception.EmosException;
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

    @Override
    public ArrayList<Integer> searchUserIdByRoleId(Integer id) {
        return roleMapper.searchUserIdByRoleId(id);
    }

    @Override
    public int update(Role role) {
        return roleMapper.update(role);
    }

    @Override
    public HashMap searchById(int id) {
        return roleMapper.searchById(id);
    }

    @Override
    public int deleteRoleByIds(Integer[] ids) {
        if (!roleMapper.searchCanDelete(ids)) {
            throw new EmosException("该角色下存在已关联用户，无法删除");
        }
        return roleMapper.deleteRoleByIds(ids);
    }
}