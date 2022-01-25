package com.mumu.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author mumu
 * @date 2022/1/26
 */
@Data
@Schema(description = "根据ID查找罚款类型表单")
public class SearchAmectTypeByIdForm {
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    @Schema(description = "罚款类型ID")
    private Integer id;
}