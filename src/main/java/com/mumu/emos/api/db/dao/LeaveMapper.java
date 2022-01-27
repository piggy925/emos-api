package com.mumu.emos.api.db.dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface LeaveMapper {
    ArrayList<HashMap> searchLeaveByPage(HashMap param);

    long searchLeaveCount(HashMap param);
}