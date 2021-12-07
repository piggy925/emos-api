package com.mumu.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.json.JSONUtil;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.common.util.R;
import com.mumu.emos.api.controller.form.SearchMeetingRoomByPageForm;
import com.mumu.emos.api.service.MeetingRoomService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/meeting_room")
public class MeetingRoomController {
    @Resource
    private MeetingRoomService meetingRoomService;

    @PostMapping("/searchMeetingRoomByPage")
    @Operation(summary = "查询会议室分页数据")
    @SaCheckLogin
    public R searchMeetingRoomByPage(@Valid @RequestBody SearchMeetingRoomByPageForm form) {
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        param.put("start", start);
        PageUtils pageUtils = meetingRoomService.searchMeetingRoomByPage(param);
        return R.ok().put("page", pageUtils);
    }
}