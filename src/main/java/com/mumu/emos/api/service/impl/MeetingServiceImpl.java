package com.mumu.emos.api.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
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

    @Override
    public ArrayList<HashMap> searchOfflineMeetingInWeek(HashMap param) {
        return meetingMapper.searchOfflineMeetingInWeeks(param);
    }

    @Override
    public HashMap searchMeetingInfo(short status, long id) {
        HashMap map;

        //正在进行与已结束的会议可以查看出席员工present与未出席员工unpresent
        if (status == 4 || status == 5) {
            map = meetingMapper.searchCurrentMeetingInfo(id);
        } else {
            map = meetingMapper.searchMeetingInfo(id);
        }
        return map;
    }

    @Override
    public int deleteMeetingApplication(HashMap param) {
        String uuid = MapUtil.getStr(param, "uuid");
        String reason = MapUtil.getStr(param, "reason");
        String instanceId = MapUtil.getStr(param, "instanceId");

        HashMap meeting = meetingMapper.searchMeetingById(param);
        int status = MapUtil.getInt(meeting, "status");
        String date = MapUtil.getStr(meeting, "date");
        String start = MapUtil.getStr(meeting, "start");
        DateTime dateTime = DateUtil.parseDate(date + " " + start);
        boolean isCreator = Boolean.parseBoolean(MapUtil.getStr(meeting, "isCreator"));

        // 只有距离开始时间大于20分钟的会议能被删除
        if (DateTime.now().isAfterOrEquals(dateTime.offset(DateField.MINUTE, -20))) {
            throw new EmosException("会议距开始不足20分钟，无法删除");
        }
        // 只有会议创建者能删除会议
        if (!isCreator) {
            throw new EmosException("您不是会议创建人，无法删除会议");
        }
        // 只有待审批与未开始的会议能被删除
        if (status == 1 || status == 3) {
            int rows = meetingMapper.deleteMeetingApplication(param);
            if (rows == 1) {
                meetingWorkFlowTask.deleteMeetingApplication(uuid, instanceId, reason);
            }
            return rows;
        } else {
            throw new EmosException("只能删除待审批和未开始的会议");
        }
    }

    @Override
    public PageUtils searchOnlineMeetingByPage(HashMap param) {
        ArrayList<HashMap> list = meetingMapper.searchOnlineMeetingByPage(param);
        long count = meetingMapper.searchOnlineMeetingCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list, count, start, length);
        return pageUtils;
    }
}