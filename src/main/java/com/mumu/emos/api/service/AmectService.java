package com.mumu.emos.api.service;

import com.mumu.emos.api.common.util.PageUtils;

import java.util.HashMap;

/**
 * @author mumu
 * @date 2022/1/1
 */
public interface AmectService {
    PageUtils searchAmectByPage(HashMap param);
}