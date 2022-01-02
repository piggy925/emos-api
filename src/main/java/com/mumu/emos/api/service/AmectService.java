package com.mumu.emos.api.service;

import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.pojo.Amect;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author mumu
 * @date 2022/1/1
 */
public interface AmectService {
    PageUtils searchAmectByPage(HashMap param);

    int insert(ArrayList<Amect> amect);


    HashMap searchById(int id);

    int update(HashMap param);

    int deleteAmectByIds(Integer[] ids);
}