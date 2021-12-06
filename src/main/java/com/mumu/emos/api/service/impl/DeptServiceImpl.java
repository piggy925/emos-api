package com.mumu.emos.api.service.impl;

import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.dao.DeptMapper;
import com.mumu.emos.api.db.pojo.Dept;
import com.mumu.emos.api.exception.EmosException;
import com.mumu.emos.api.service.DeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class DeptServiceImpl implements DeptService {
    @Resource
    private DeptMapper deptMapper;

    @Override
    public ArrayList<HashMap> searchAllDept() {
        ArrayList<HashMap> list = deptMapper.searchAllDept();
        return list;
    }

    @Override
    public HashMap searchById(int id) {
        HashMap map = deptMapper.searchById(id);
        return map;
    }

    @Override
    public PageUtils searchDeptByPage(HashMap param) {
        ArrayList<HashMap> list = deptMapper.searchDeptByPage(param);
        long count = deptMapper.searchDeptCount(param);
        int start = (int) param.get("start");
        int length = (int) param.get("length");
        return new PageUtils(list, count, start, length);
    }

    @Override
    public int insert(Dept dept) {
        return deptMapper.insert(dept);
    }

    @Override
    public int update(Dept dept) {
        return deptMapper.update(dept);
    }

    @Override
    public int deleteDeptByIds(Integer[] ids) {
        boolean canDelete = deptMapper.searchCanDelete(ids);
        if (!canDelete) {
            throw new EmosException("该部门下存在用户，无法删除");
        }
        return deptMapper.deleteDeptByIds(ids);
    }
}