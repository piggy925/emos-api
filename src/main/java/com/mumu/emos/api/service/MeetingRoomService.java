package com.mumu.emos.api.service;

import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.pojo.MeetingRoom;

import java.util.HashMap;

public interface MeetingRoomService {
    PageUtils searchMeetingRoomByPage(HashMap param);

    int insert(MeetingRoom meetingRoom);
}