package com.mumu.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.json.JSONUtil;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.common.util.R;
import com.mumu.emos.api.controller.form.InsertAmectTypeForm;
import com.mumu.emos.api.controller.form.SearchAmectTypeByIdForm;
import com.mumu.emos.api.controller.form.SearchAmectTypeByPageForm;
import com.mumu.emos.api.controller.form.UpdateAmectTypeByIdForm;
import com.mumu.emos.api.db.pojo.AmectType;
import com.mumu.emos.api.service.AmectTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author mumu
 * @date 2022/1/1
 */
@RestController
@RequestMapping("/amect_type")
@Tag(name = "AmectTypeController", description = "罚款类型Web接口")
public class AmectTypeController {
    @Resource
    private AmectTypeService amectTypeService;

    @GetMapping("/searchAllAmectType")
    @Operation(summary = "查询所有罚款类型")
    @SaCheckLogin
    public R searchAllAmectType() {
        ArrayList<AmectType> list = amectTypeService.searchAllAmectType();
        return R.ok().put("list", list);
    }

    @PostMapping("/searchAmectTypeByPage")
    @Operation(summary = "查询罚款类型分页记录")
    @SaCheckPermission(value = {"ROOT"})
    public R searchAmectTypeByPage(@Valid @RequestBody SearchAmectTypeByPageForm form) {
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        param.put("start", start);
        PageUtils pageUtils = amectTypeService.searchAmectTypeByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加罚款类型")
    @SaCheckPermission(value = {"ROOT"})
    public R insert(@Valid @RequestBody InsertAmectTypeForm form) {
        AmectType amectType = JSONUtil.parse(form).toBean(AmectType.class);
        int rows = amectTypeService.insert(amectType);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/searchById")
    @Operation(summary = "根据ID查找罚款类型")
    @SaCheckPermission(value = {"ROOT"})
    public R searchById(@Valid @RequestBody SearchAmectTypeByIdForm form) {
        HashMap map = amectTypeService.searchById(form.getId());
        return R.ok(map);
    }

    @PostMapping("/update")
    @Operation(summary = "更新罚款类型")
    @SaCheckPermission(value = {"ROOT"})
    public R update(@Valid @RequestBody UpdateAmectTypeByIdForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        int rows = amectTypeService.update(param);
        return R.ok().put("rows", rows);
    }
}