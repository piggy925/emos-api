package com.mumu.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author mumu
 * @date 2022/1/26
 */
@Data
@Schema(description = "删除罚款类型记录")
public class DeleteAmectTypeByIdsForm {
    @NotEmpty(message = "ids不能为空")
    @Schema(description = "罚款类型编号")
    private Integer[] ids;
}