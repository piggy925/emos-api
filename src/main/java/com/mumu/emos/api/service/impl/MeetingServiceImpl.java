package com.mumu.emos.api.service.impl;

import cn.hutool.json.JSONUtil;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.dao.MeetingMapper;
import com.mumu.emos.api.service.MeetingService;
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
}