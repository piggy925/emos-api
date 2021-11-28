package com.mumu.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.mumu.emos.api.common.util.R;
import com.mumu.emos.api.controller.form.LoginForm;
import com.mumu.emos.api.controller.form.UpdatePasswordForm;
import com.mumu.emos.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "用户Web接口")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/test")
    @Operation(summary = "test")
    public R test() {
        return R.ok().put("result", true);
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public R login(@Valid @RequestBody LoginForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        Integer userId = userService.login(param);
        R r = R.ok().put("result", userId != null);
        if (userId != null) {
            StpUtil.login(userId);
            Set<String> permissions = userService.searchUserPermissions(userId);
            String token = StpUtil.getTokenInfo().getTokenValue();
            r.put("permissions", permissions).put("token", token);
        }
        return r;
    }

    /**
     * 登陆成功后加载用户的基本信息
     */
    @GetMapping("/loadUserInfo")
    @Operation(summary = "登陆成功后加载用户的基本信息")
    @SaCheckLogin
    public R loadUserInfo() {
        int userId = StpUtil.getLoginIdAsInt();
        HashMap summary = userService.searchUserSummary(userId);
        return R.ok(summary);
    }

    /**
     * 修改密码
     */
    @PostMapping("/updatePassword")
    @Operation(summary = "修改密码")
    @SaCheckLogin
    public R updatePassword(@Valid @RequestBody UpdatePasswordForm form) {
        int userId = StpUtil.getLoginIdAsInt();
        HashMap param = new HashMap();
        param.put("userId", userId);
        param.put("password", form.getPassword());
        int rows = userService.updatePassword(param);
        return R.ok().put("rows", rows);
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    @Operation(summary = "退出登录")
    public R logout() {
        StpUtil.logout();
        return R.ok();
    }
}