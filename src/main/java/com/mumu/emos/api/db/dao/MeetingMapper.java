package com.mumu.emos.api.db.dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface MeetingMapper {
    ArrayList<HashMap> searchOfflineMeetingByPage(HashMap param);

    long searchOfflineMeetingCount();
}