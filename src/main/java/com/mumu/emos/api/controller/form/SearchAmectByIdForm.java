package com.mumu.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author mumu
 * @date 2022/1/2
 */
@Data
@Schema(description = "根据ID查询罚款记录表单")
public class SearchAmectByIdForm {
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    @Schema(description = "罚款记录ID")
    private Integer id;
}