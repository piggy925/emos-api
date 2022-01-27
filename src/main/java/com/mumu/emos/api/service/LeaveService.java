package com.mumu.emos.api.service;

import com.mumu.emos.api.common.util.PageUtils;

import java.util.HashMap;

/**
 * @author mumu
 * @date 2022/1/27
 */
public interface LeaveService {
    PageUtils searchLeaveByPage(HashMap param);
}