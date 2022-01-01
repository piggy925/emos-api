package com.mumu.emos.api.db.dao;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author mumu
 * @date 2022/1/1
 */
public interface AmectMapper {
    ArrayList<HashMap> searchAmectByPage(HashMap param);

    long searchAmectCount(HashMap param);
}