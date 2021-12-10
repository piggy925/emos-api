package com.mumu.emos.api.service;

import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.pojo.Meeting;

import java.util.HashMap;

public interface MeetingService {
    PageUtils searchOfflineMeetingByPage(HashMap param);

    int insert(Meeting meeting);
}