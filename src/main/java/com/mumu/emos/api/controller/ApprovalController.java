package com.mumu.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.common.util.R;
import com.mumu.emos.api.controller.form.ApprovalTaskForm;
import com.mumu.emos.api.controller.form.SearchApprovalContentForm;
import com.mumu.emos.api.controller.form.SearchTaskByPageForm;
import com.mumu.emos.api.exception.EmosException;
import com.mumu.emos.api.service.ApprovalService;
import com.mumu.emos.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/approval")
@Tag(name = "ApprovalController", description = "任务审批Web接口")
public class ApprovalController {
    @Value("${workflow.url}")
    private String workflow;

    @Value("${emos.code}")
    private String code;

    @Value("${emos.tcode}")
    private String tcode;

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

    @PostMapping("/searchApprovalContent")
    @Operation(summary = "查询审批任务详情")
    @SaCheckPermission(value = {"WORKFLOW:APPROVAL", "FILE:ARCHIVE"}, mode = SaMode.OR)
    public R searchApprovalContent(@Valid @RequestBody SearchApprovalContentForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        int userId = StpUtil.getLoginIdAsInt();
        param.put("userId", userId);
        param.put("role", userService.searchUserRoles(userId));
        HashMap content = approvalService.searchApprovalContent(param);
        return R.ok().put("content", content);
    }

    @GetMapping("/searchApprovalBpmn")
    @Operation(summary = "获取BPMN")
    @SaCheckPermission(value = {"WORKFLOW:APPROVAL", "FILE:ARCHIVE"}, mode = SaMode.OR)
    public void searchApprovalBpmn(String instanceId, HttpServletResponse response) {
        if (StrUtil.isBlankIfStr(instanceId)) {
            throw new EmosException("instanceId不能为空");
        }
        HashMap param = new HashMap() {{
            put("instanceId", instanceId);
            put("code", code);
            put("tcode", tcode);
        }};
        String url = workflow + "/workflow/searchApprovalBpmn";
        HttpResponse resp = HttpRequest.post(url).header("Content-Type", "application/json").body(JSONUtil.toJsonStr(param)).execute();
        if (resp.getStatus() == 200) {
            try (
                    InputStream in = resp.bodyStream();
                    BufferedInputStream bin = new BufferedInputStream(in);
                    ServletOutputStream out = response.getOutputStream();
                    BufferedOutputStream bout = new BufferedOutputStream(out)
            ) {
                IoUtil.copy(bin, bout);
            } catch (Exception e) {
                log.error("执行异常");
            }
        } else {
            log.error("获取工作流BPMN失败");
            throw new EmosException("获取工作流BPMN失败");
        }
    }

    @PostMapping("/approvalTask")
    @Operation(summary = "审批任务")
    @SaCheckPermission(value = {"WORKFLOW:APPROVAL"})
    public R approvalTask(@Valid @RequestBody ApprovalTaskForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        approvalService.approvalTask(param);
        return R.ok();
    }
}