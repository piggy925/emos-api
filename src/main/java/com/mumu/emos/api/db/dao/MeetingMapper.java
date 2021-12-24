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

    ArrayList<HashMap> searchOfflineMeetingInWeeks(HashMap param);

    HashMap searchMeetingInfo(long id);

    HashMap searchCurrentMeetingInfo(long id);

    int deleteMeetingApplication(HashMap param);

    HashMap searchMeetingById(HashMap param);

    ArrayList<HashMap> searchOnlineMeetingByPage(HashMap param);

    long searchOnlineMeetingCount(HashMap param);

    ArrayList<HashMap> searchOnlineMeetingMembers(HashMap param);

    long searchCanCheckinMeeting(HashMap param);

    int updateMeetingPresent(HashMap param);
}