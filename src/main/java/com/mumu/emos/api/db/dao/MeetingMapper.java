package com.mumu.emos.api.db.dao;

import com.mumu.emos.api.db.pojo.Meeting;

import java.util.ArrayList;
import java.util.HashMap;

public interface MeetingMapper {
    ArrayList<HashMap> searchOfflineMeetingByPage(HashMap param);

    long searchOfflineMeetingCount();

    int updateMeetingInstanceId(HashMap param);

    boolean searchMeetingMembersInSameDept(String uuid);

    int insert(Meeting meeting);
}