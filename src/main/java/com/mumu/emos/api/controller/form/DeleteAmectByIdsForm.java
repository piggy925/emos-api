package com.mumu.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author mumu
 * @date 2022/1/2
 */
@Data
@Schema(description = "删除罚款记录表单")
public class DeleteAmectByIdsForm {
    @NotEmpty(message = "ids不能为空")
    @Schema(description = "罚款记录主键")
    private Integer[] ids;
}