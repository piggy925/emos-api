package com.mumu.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.mumu.emos.api.common.util.R;
import com.mumu.emos.api.db.pojo.AmectType;
import com.mumu.emos.api.service.AmectTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

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
}