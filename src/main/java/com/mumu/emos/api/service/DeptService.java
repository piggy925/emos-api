package com.mumu.emos.api.service;

import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.pojo.Dept;

import java.util.ArrayList;
import java.util.HashMap;

public interface DeptService {
    ArrayList<HashMap> searchAllDept();

    HashMap searchById(int id);

    PageUtils searchDeptByPage(HashMap param);

    int insert(Dept dept);
}