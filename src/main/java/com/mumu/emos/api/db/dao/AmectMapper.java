package com.mumu.emos.api.db.dao;

import com.mumu.emos.api.db.pojo.Amect;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author mumu
 * @date 2022/1/1
 */
public interface AmectMapper {
    ArrayList<HashMap> searchAmectByPage(HashMap param);

    long searchAmectCount(HashMap param);

    int insert(Amect amect);

    HashMap searchById(int id);

    int update(HashMap param);
}