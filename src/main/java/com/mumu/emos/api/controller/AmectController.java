package com.mumu.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.common.util.R;
import com.mumu.emos.api.controller.form.*;
import com.mumu.emos.api.db.pojo.Amect;
import com.mumu.emos.api.service.AmectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Operation(summary = "新增罚款记录")
    @PostMapping("/insert")
    @SaCheckPermission(value = {"AMECT:INSERT", "ROOT"}, mode = SaMode.OR)
    public R insert(@Valid @RequestBody InsertAmectForm form) {
        ArrayList<Amect> list = new ArrayList<>();
        for (Integer userId : form.getUserId()) {
            Amect amect = new Amect();
            amect.setAmount(new BigDecimal(form.getAmount()));
            amect.setTypeId(form.getTypeId());
            amect.setUserId(userId);
            amect.setReason(form.getReason());
            amect.setUuid(IdUtil.simpleUUID());
            list.add(amect);
        }
        int rows = amectService.insert(list);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/searchById")
    @Operation(summary = "根据ID查找罚款记录")
    @SaCheckPermission(value = {"ROOT", "AMECT:SELECT"}, mode = SaMode.OR)
    public R searchById(@Valid @RequestBody SearchAmectByIdForm form) {
        HashMap map = amectService.searchById(form.getId());
        return R.ok(map);
    }

    @PostMapping("/update")
    @Operation(summary = "更新罚款记录")
    @SaCheckPermission(value = {"ROOT", "AMECT:UPDATE"}, mode = SaMode.OR)
    public R update(@Valid @RequestBody UpdateAmectForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        int rows = amectService.update(param);
        return R.ok().put("rows", rows);
    }

    @DeleteMapping("/deleteAmectByIds")
    @Operation(summary = "删除罚款记录")
    @SaCheckPermission(value = {"ROOT", "AMECT:DELETE"}, mode = SaMode.OR)
    public R delete(@Valid @RequestBody DeleteAmectByIdsForm form) {
        int rows = amectService.deleteAmectByIds(form.getIds());
        return R.ok().put("rows", rows);
    }
}