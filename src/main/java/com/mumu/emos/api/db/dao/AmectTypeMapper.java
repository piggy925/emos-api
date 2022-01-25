package com.mumu.emos.api.db.dao;

import com.mumu.emos.api.db.pojo.AmectType;

import java.util.ArrayList;
import java.util.HashMap;

public interface AmectTypeMapper {
    ArrayList<AmectType> searchAllAmectType();

    ArrayList<HashMap> searchAmectTypeByPage(HashMap param);

    long searchAmectTypeCount(HashMap param);

    int insert(AmectType amectType);

    HashMap searchById(int id);

    int update(HashMap param);

    int deleteAmectTypeByIds(Integer[] ids);
}