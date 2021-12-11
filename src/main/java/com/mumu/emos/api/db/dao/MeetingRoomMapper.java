package com.mumu.emos.api.db.dao;

import com.mumu.emos.api.db.pojo.MeetingRoom;

import java.util.ArrayList;
import java.util.HashMap;

public interface MeetingRoomMapper {
    ArrayList<String> searchFreeMeetingRoom(HashMap param);

    ArrayList<HashMap> searchMeetingRoomByPage(HashMap param);

    long searchMeetingRoomCount(HashMap param);

    int insert(MeetingRoom meetingRoom);

    int update(MeetingRoom meetingRoom);

    HashMap searchById(int id);

    boolean searchCanDelete(Integer[] ids);

    int deleteMeetingRoomByIds(Integer[] ids);

    ArrayList<HashMap> searchAllMeetingRoom();
}