package com.mumu.emos.api.service;

import com.mumu.emos.api.common.util.PageUtils;

import java.util.HashMap;

public interface MeetingService {
    PageUtils searchOfflineMeetingByPage(HashMap param);
}