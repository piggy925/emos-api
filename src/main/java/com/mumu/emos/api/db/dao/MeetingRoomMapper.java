package com.mumu.emos.api.db.dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface MeetingRoomMapper {
    ArrayList<HashMap> searchMeetingRoomByPage(HashMap param);

    long searchMeetingRoomCount(HashMap param);
}