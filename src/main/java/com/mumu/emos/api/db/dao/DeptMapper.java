package com.mumu.emos.api.db.dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface DeptMapper {
    ArrayList<HashMap> searchAllDept();

    HashMap searchById(int id);
}