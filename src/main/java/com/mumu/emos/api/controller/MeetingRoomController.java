package com.mumu.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.json.JSONUtil;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.common.util.R;
import com.mumu.emos.api.controller.form.InsertMeetingRoomForm;
import com.mumu.emos.api.controller.form.SearchMeetingRoomByIdForm;
import com.mumu.emos.api.controller.form.SearchMeetingRoomByPageForm;
import com.mumu.emos.api.controller.form.UpdateMeetingRoomForm;
import com.mumu.emos.api.db.pojo.MeetingRoom;
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

    @PostMapping("/insert")
    @Operation(summary = "添加会议室")
    @SaCheckPermission(value = {"ROOT", "MEETING_ROOM:INSERT"}, mode = SaMode.OR)
    public R insert(@Valid @RequestBody InsertMeetingRoomForm form) {
        MeetingRoom meetingRoom = JSONUtil.parse(form).toBean(MeetingRoom.class);
        int rows = meetingRoomService.insert(meetingRoom);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/update")
    @Operation(summary = "修改会议室")
    @SaCheckPermission(value = {"ROOT", "MEETING_ROOM:UPDATE"}, mode = SaMode.OR)
    public R update(@Valid @RequestBody UpdateMeetingRoomForm form) {
        MeetingRoom meetingRoom = JSONUtil.parse(form).toBean(MeetingRoom.class);
        int rows = meetingRoomService.update(meetingRoom);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/searchById")
    @Operation(summary = "根据ID查找会议室")
    @SaCheckPermission(value = {"ROOT", "MEETING_ROOM:SELECT"}, mode = SaMode.OR)
    public R searchById(@Valid @RequestBody SearchMeetingRoomByIdForm form) {
        HashMap map = meetingRoomService.searchById(form.getId());
        return R.ok(map);
    }
}