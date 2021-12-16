package com.mumu.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.common.util.R;
import com.mumu.emos.api.controller.form.SearchTaskByPageForm;
import com.mumu.emos.api.service.ApprovalService;
import com.mumu.emos.api.service.UserService;
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
@RequestMapping("/approval")
@Tag(name = "ApprovalController", description = "任务审批Web接口")
public class ApprovalController {
    @Resource
    private UserService userService;

    @Resource
    private ApprovalService approvalService;

    @PostMapping("/searchTaskByPage")
    @Operation(summary = "查询分页审批任务列表")

    @SaCheckPermission(value = {"WORKFLOW:APPROVAL", "FILE:ARCHIVE"}, mode = SaMode.OR)
    public R searchTaskByPage(@Valid @RequestBody SearchTaskByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        int userId = StpUtil.getLoginIdAsInt();
        param.put("userId", userId);
        param.put("role", userService.searchUserRoles(userId));
        PageUtils pageUtils = approvalService.searchTaskByPage(param);
        return R.ok().put("page", pageUtils);
    }
}