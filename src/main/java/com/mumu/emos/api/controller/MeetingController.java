package com.mumu.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.common.util.R;
import com.mumu.emos.api.controller.form.SearchOfflineMeetingByPageForm;
import com.mumu.emos.api.service.MeetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/meeting")
@Tag(name = "MeetingController", description = "会议Web接口")
public class MeetingController {
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
}