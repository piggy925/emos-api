package com.mumu.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "密码修改表单类")
public class UpdatePasswordForm {
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "密码格式不正确")
    private String password;
}