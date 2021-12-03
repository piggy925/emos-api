package com.mumu.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.mumu.emos.api.common.util.R;
import com.mumu.emos.api.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    @GetMapping("/searchAllPermission")
    @Operation(summary = "查询所有权限")
    @SaCheckPermission(value = {"ROOT", "ROLE:INSERT", "ROLE:UPDATE"}, mode = SaMode.OR)
    public R searchAllPermission() {
        ArrayList<HashMap> list = permissionService.searchAllPermission();
        return R.ok().put("list", list);
    }
}