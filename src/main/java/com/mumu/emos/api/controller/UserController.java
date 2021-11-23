package com.mumu.emos.api.controller;

import com.mumu.emos.api.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "用户Web接口")
public class UserController {
    @PostMapping("/test")
    @Operation(summary = "test")
    public R test() {
        return R.ok().put("result", true);
    }
}