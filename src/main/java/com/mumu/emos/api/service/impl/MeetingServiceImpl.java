package com.mumu.emos.api.service.impl;

import cn.hutool.json.JSONUtil;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.dao.MeetingMapper;
import com.mumu.emos.api.db.pojo.Meeting;
import com.mumu.emos.api.exception.EmosException;
import com.mumu.emos.api.service.MeetingService;
import com.mumu.emos.api.task.MeetingWorkFlowTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Service
public class MeetingServiceImpl implements MeetingService {
    @Resource
    private MeetingMapper meetingMapper;
    @Resource
    private MeetingWorkFlowTask meetingWorkFlowTask;

    @Override
    public PageUtils searchOfflineMeetingByPage(HashMap param) {
        ArrayList<HashMap> list = meetingMapper.searchOfflineMeetingByPage(param);
        list.forEach(one -> {
            String meeting = (String) one.get("meeting");
            if (!ObjectUtils.isEmpty(meeting)) {
                one.replace("meeting", JSONUtil.parseArray(meeting));
            }
        });
        long count = meetingMapper.searchOfflineMeetingCount();
        int start = (int) param.get("start");
        int length = (int) param.get("length");
        return new PageUtils(list, count, start, length);
    }

    @Override
    public int insert(Meeting meeting) {
        int rows = meetingMapper.insert(meeting);
        if (rows != 1) {
            throw new EmosException("会议添加失败");
        }
        meetingWorkFlowTask.startMeetingWorkFlow(meeting.getUuid(), meeting.getCreatorId(), meeting.getTitle(), meeting.getDate(), meeting.getStart() + ":00", "线下会议");
        return rows;
    }
}