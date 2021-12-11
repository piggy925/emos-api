package com.mumu.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.common.util.R;
import com.mumu.emos.api.controller.form.*;
import com.mumu.emos.api.db.pojo.User;
import com.mumu.emos.api.exception.EmosException;
import com.mumu.emos.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
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

    @PostMapping("/searchById")
    @Operation(summary = "根据ID查找用户")
    @SaCheckPermission(value = {"ROOT", "USER:SELECT"}, mode = SaMode.OR)
    public R searchById(@Valid @RequestBody SearchUserByIdForm form) {
        HashMap map = userService.searchById(form.getUserId());
        return R.ok(map);
    }

    /**
     * 修改密码
     */
    @PostMapping("/updatePassword")
    @Operation(summary = "修改密码")
    @SaCheckLogin
    public R updatePassword(@Valid @RequestBody UpdatePasswordForm form) {
        int userId = StpUtil.getLoginIdAsInt();
        HashMap param = new HashMap() {
            {
                put("userId", userId);
                put("oldPassword", form.getOldPassword());
                put("password", form.getPassword());
            }
        };
        Integer result = userService.validatePassword(param);
        if (ObjectUtils.isEmpty(result)) {
            throw new EmosException("旧密码输入不正确");
        }
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

    /**
     * 分页查询用户
     */
    @PostMapping("/searchUserByPage")
    @Operation(summary = "分页查询用户")
    @SaCheckPermission(value = {"ROOT", "USER:SELECT"}, mode = SaMode.OR)
    public R searchUserByPage(@Valid @RequestBody SearchUserByPageForm form) {
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        param.put("start", start);
        PageUtils pageUtils = userService.searchUserByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加用户")
    @SaCheckPermission(value = {"ROOT", "USER:INSERT"}, mode = SaMode.OR)
    public R insert(@Valid @RequestBody InsertUserForm form) {
        User user = JSONUtil.parse(form).toBean(User.class);
        user.setRole(JSONUtil.parseArray(form.getRole()).toString());
        user.setStatus((byte) 1);
        user.setCreateTime(new Date());
        int rows = userService.insert(user);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/update")
    @Operation(summary = "修改用户信息")
    @SaCheckPermission(value = {"ROOT", "USER:UPDATE"}, mode = SaMode.OR)
    public R update(@Valid @RequestBody UpdateUserForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        param.replace("role", JSONUtil.parseArray(form.getRole()).toString());
        int rows = userService.update(param);
        if (rows == 1) {
            StpUtil.logout(form.getUserId());
        }
        return R.ok().put("rows", rows);
    }

    @DeleteMapping("/deleteUserByIds")
    @Operation(summary = "批量删除用户")
    @SaCheckPermission(value = {"ROOT", "USER:DELETE"}, mode = SaMode.OR)
    public R deleteUserByIds(@Valid @RequestBody DeleteUserByIdsForm form) {
        Integer userId = StpUtil.getLoginIdAsInt();
        Integer[] ids = form.getIds();
        if (ArrayUtil.contains(ids, userId)) {
            return R.error("您不能删除自己的账户");
        }
        int rows = userService.deleteUserByIds(ids);
        if (rows > 0) {
            for (Integer id : form.getIds()) {
                StpUtil.logout(id);
            }
        }
        return R.ok().put("rows", rows);
    }

    @GetMapping("/searchAllUser")
    @Operation(summary = "查询所有用户")
    @SaCheckLogin
    public R searchAllUser() {
        ArrayList<HashMap> list = userService.searchAllUser();
        return R.ok().put("list", list);
    }
}