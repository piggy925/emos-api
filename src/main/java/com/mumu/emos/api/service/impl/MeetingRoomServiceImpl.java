package com.mumu.emos.api.service.impl;

import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.dao.MeetingRoomMapper;
import com.mumu.emos.api.db.pojo.MeetingRoom;
import com.mumu.emos.api.service.MeetingRoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {
    @Resource
    private MeetingRoomMapper meetingRoomMapper;

    @Override
    public PageUtils searchMeetingRoomByPage(HashMap param) {
        ArrayList<HashMap> list = meetingRoomMapper.searchMeetingRoomByPage(param);
        long count = meetingRoomMapper.searchMeetingRoomCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list, count, start, length);
        return pageUtils;
    }

    @Override
    public int insert(MeetingRoom meetingRoom) {
        return meetingRoomMapper.insert(meetingRoom);
    }

    @Override
    public int update(MeetingRoom meetingRoom) {
        return meetingRoomMapper.update(meetingRoom);
    }

    @Override
    public HashMap searchById(int id) {
        return meetingRoomMapper.searchById(id);
    }
}