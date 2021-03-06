package com.mumu.emos.api.db.dao;

import com.mumu.emos.api.db.pojo.Dept;

import java.util.ArrayList;
import java.util.HashMap;

public interface DeptMapper {
    ArrayList<HashMap> searchAllDept();

    HashMap searchById(int id);

    ArrayList<HashMap> searchDeptByPage(HashMap param);

    long searchDeptCount(HashMap param);

    int insert(Dept dept);

    int update(Dept dept);

    boolean searchCanDelete(Integer[] ids);

    int deleteDeptByIds(Integer[] ids);
}