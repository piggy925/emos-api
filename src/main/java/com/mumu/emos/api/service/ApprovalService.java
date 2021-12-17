package com.mumu.emos.api.service;

import com.mumu.emos.api.common.util.PageUtils;

import java.util.HashMap;

public interface ApprovalService {
    PageUtils searchTaskByPage(HashMap param);

    HashMap searchApprovalContent(HashMap param);
}