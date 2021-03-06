package com.mumu.emos.api.service;

import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.pojo.MeetingRoom;

import java.util.ArrayList;
import java.util.HashMap;

public interface MeetingRoomService {
    ArrayList<String> searchFreeMeetingRoom(HashMap param);

    PageUtils searchMeetingRoomByPage(HashMap param);

    int insert(MeetingRoom meetingRoom);

    int update(MeetingRoom meetingRoom);

    HashMap searchById(int id);

    int deleteMeetingRoomByIds(Integer[] ids);

    ArrayList<HashMap> searchAllMeetingRoom();
}