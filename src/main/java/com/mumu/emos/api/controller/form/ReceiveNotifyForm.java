package com.mumu.emos.api.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReceiveNotifyForm {
    @NotBlank
    private String processId;
    @NotBlank
    private String uuid;
    @NotBlank
    private String result;
}