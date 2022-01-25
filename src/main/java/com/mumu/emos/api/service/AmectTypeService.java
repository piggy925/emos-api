package com.mumu.emos.api.service;

import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.pojo.AmectType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author mumu
 * @date 2022/1/1
 */
public interface AmectTypeService {
    ArrayList<AmectType> searchAllAmectType();

    PageUtils searchAmectTypeByPage(HashMap param);

    int insert(AmectType amectType);
}