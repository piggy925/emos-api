package com.mumu.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.common.util.R;
import com.mumu.emos.api.config.tencent.TrtcUtil;
import com.mumu.emos.api.controller.form.*;
import com.mumu.emos.api.db.pojo.Meeting;
import com.mumu.emos.api.exception.EmosException;
import com.mumu.emos.api.service.MeetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/meeting")
@Tag(name = "MeetingController", description = "会议Web接口")
public class MeetingController {
    @Value("${tencent.trtc.appId}")
    private int appId;

    @Resource
    private TrtcUtil trtcUtil;

    @Resource
    private MeetingService meetingService;

    @PostMapping("/searchOfflineMeetingByPage")
    @Operation(summary = "查询线下会议分页数据")
    @SaCheckLogin
    public R searchOfflineMeetingByPage(@Valid @RequestBody SearchOfflineMeetingByPageForm form) {
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        HashMap param = new HashMap() {{
            put("date", form.getDate());
            put("mold", form.getMold());
            put("userId", StpUtil.getLoginId());
            put("start", start);
            put("length", length);
        }};
        PageUtils pageUtils = meetingService.searchOfflineMeetingByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加会议")
    @SaCheckLogin
    public R insert(@Valid @RequestBody InsertMeetingForm form) {
        DateTime start = DateUtil.parse(form.getDate() + " " + form.getStart());
        DateTime end = DateUtil.parse(form.getDate() + " " + form.getEnd());
        if (start.isAfterOrEquals(end)) {
            throw new EmosException("结束时间必须大于开始时间");
        }
        if ((new DateTime()).isAfterOrEquals(start)) {
            throw new EmosException("开始时间必须大于当前时间");
        }
        Meeting meeting = JSONUtil.parse(form).toBean(Meeting.class);
        meeting.setUuid(UUID.randomUUID().toString(true));
        meeting.setCreatorId(StpUtil.getLoginIdAsInt());
        meeting.setStatus((short) 1);
        int rows = meetingService.insert(meeting);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/receiveNotify")
    @Operation(summary = "接收工作流通知")
    public R receiveNotify(@Valid @RequestBody ReceiveNotifyForm form) {
        if (form.getResult().equals("同意")) {
            log.debug(form.getUuid() + "的会议审批通过");
        } else {
            log.debug(form.getUuid() + "的会议审批不通过");
        }
        return R.ok();
    }

    @PostMapping("/searchOfflineMeetingByWeek")
    @Operation(summary = "查询某个会议室一周的会议")
    @SaCheckLogin
    public R searchOfflineMeetingByWeek(@Valid @RequestBody SearchOfflineMeetingInWeekForm form) {
        String date = form.getDate();
        DateTime startDate, endDate;
        if (!ObjectUtils.isEmpty(date)) {
            // 传入的日期不为空，则查询传入日期及之后6天的会议信息
            startDate = DateUtil.parseDate(date);
            endDate = startDate.offsetNew(DateField.DAY_OF_WEEK, 6);
        } else {
            // 传入的日期为空，则查询当天所在周的会议信息
            startDate = DateUtil.beginOfWeek(new Date());
            endDate = DateUtil.endOfWeek(new Date());
        }
        HashMap param = new HashMap() {{
            put("place", form.getName());
            put("start", startDate.toDateStr());
            put("end", endDate.toDateStr());
            put("mold", form.getMold());
            put("userId", StpUtil.getLoginIdAsLong());
        }};
        ArrayList<HashMap> list = meetingService.searchOfflineMeetingInWeek(param);

        // 前端进行日期偏移较麻烦，在后端使用工具类将日期处理好后返回给前端
        ArrayList<Object> days = new ArrayList<>();
        DateRange dateRange = DateUtil.range(startDate, endDate, DateField.DAY_OF_WEEK);
        dateRange.forEach(one -> {
            JSONObject json = new JSONObject();
            json.set("date", one.toString("MM/dd"));
            json.set("day", one.dayOfWeekEnum().toChinese("周"));
            days.add(json);
        });
        return R.ok().put("list", list).put("days", days);
    }

    @PostMapping("/searchMeetingInfo")
    @Operation(summary = "查询会议信息")
    @SaCheckLogin
    public R searchMeetingInfo(@Valid @RequestBody SearchMeetingInfoForm form) {
        HashMap map = meetingService.searchMeetingInfo(form.getStatus(), form.getId());
        return R.ok(map);
    }

    @PostMapping("/deleteMeetingApplication")
    @Operation(summary = "删除会议")
    @SaCheckLogin
    public R deleteMeetingApplication(@Valid @RequestBody DeleteMeetingApplicationForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        param.put("creatorId", StpUtil.getLoginIdAsLong());
        param.put("userId", StpUtil.getLoginIdAsLong());
        int rows = meetingService.deleteMeetingApplication(param);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/searchOnlineMeetingByPage")
    @Operation(summary = "查询线上会议分页数据")
    @SaCheckLogin
    public R searchOnlineMeetingByPage(@Valid @RequestBody SearchOnlineMeetingByPageForm form) {
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        HashMap param = new HashMap() {{
            put("date", form.getDate());
            put("mold", form.getMold());
            put("userId", StpUtil.getLoginId());
            put("start", start);
            put("length", length);
        }};
        PageUtils pageUtils = meetingService.searchOnlineMeetingByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @GetMapping("/searchMyUserSig")
    @Operation(summary = "获取用户签名")
    @SaCheckLogin
    public R searchMyUserSig() {
        String userId = StpUtil.getLoginIdAsString();
        String userSig = trtcUtil.genUserSig(userId);
        return R.ok().put("userSig", userSig).put("userId", userId).put("appId", appId);
    }

    @PostMapping("/searchRoomIdByUUID")
    @Operation(summary = "查询线上会议roomId")
    @SaCheckLogin
    public R searchRoomIdByUUID(@Valid @RequestBody SearchRoomIdByUUIDForm form) {
        Long roomId = meetingService.searchRoomIdByUUID(form.getUuid());
        return R.ok().put("roomId", roomId);
    }

    @PostMapping("/searchOnlineMeetingMembers")
    @Operation(summary = "查询线上会议参会人")
    @SaCheckLogin
    public R searchOnlineMeetingMembers(@Valid @RequestBody SearchOnlineMeetingMembersForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        param.put("userId", StpUtil.getLoginIdAsInt());
        ArrayList<HashMap> list = meetingService.searchOnlineMeetingMembers(param);
        return R.ok().put("list", list);
    }

    @PostMapping("/updateMeetingPresent")
    @Operation(summary = "线上会议签到")
    @SaCheckLogin
    public R updateMeetingPresent(@Valid @RequestBody UpdateMeetingPresentForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        param.put("userId", StpUtil.getLoginIdAsInt());

        int rows = 0;
        boolean canCheckin = meetingService.searchCanCheckinMeeting(param);
        if (canCheckin) {
            rows = meetingService.updateMeetingPresent(param);
        }
        return R.ok().put("rows", rows);
    }
}