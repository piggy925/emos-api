package com.mumu.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.common.util.R;
import com.mumu.emos.api.controller.form.SearchAmectByPageForm;
import com.mumu.emos.api.service.AmectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * @author mumu
 * @date 2022/1/1
 */
@RestController
@RequestMapping("/amect")
@Tag(name = "AmectController", description = "罚款Web接口")
@Slf4j
public class AmectController {
    @Resource
    private AmectService amectService;

    @Operation(summary = "分页查询罚款清单")
    @PostMapping("/searchAmectByPage")
    @SaCheckLogin
    public R searchAmectByPage(@Valid @RequestBody SearchAmectByPageForm form) {
        if ((form.getStartDate() == null && form.getEndDate() != null) || (form.getStartDate() != null && form.getEndDate() == null)) {
            return R.error("startData与endDate需同时为空或都不为空");
        }
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        param.put("start", start);
        int userId = StpUtil.getLoginIdAsInt();
        param.put("currentUserId", userId);

        // 如果用户没有查看罚款清单权限或超级管理员权限，则只能查看自己的罚款信息
        if (!(StpUtil.hasPermission("AMECT:SELECT") || StpUtil.hasPermission("ROOT"))) {
            param.put("userId", userId);
        }
        PageUtils pageUtils = amectService.searchAmectByPage(param);
        return R.ok().put("page", pageUtils);
    }
}