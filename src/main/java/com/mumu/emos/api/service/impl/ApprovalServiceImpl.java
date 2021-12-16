package com.mumu.emos.api.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.exception.EmosException;
import com.mumu.emos.api.service.ApprovalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Service
public class ApprovalServiceImpl implements ApprovalService {
    @Value("${workflow.url}")
    private String workflow;

    @Value("${emos.code}")
    private String code;

    @Value("${emos.tcode}")
    private String tcode;

    @Override
    public PageUtils searchTaskByPage(HashMap param) {
        param.put("code", code);
        param.put("tcode", tcode);
        String url = workflow + "/workflow/searchTaskByPage";
        HttpResponse resp = HttpRequest.post(url).header("Content-Type", "application/json").body(JSONUtil.toJsonStr(param)).execute();
        if (resp.getStatus() == 200) {
            JSONObject json = JSONUtil.parseObj(resp.body());
            JSONObject page = json.getJSONObject("page");
            ArrayList list = page.get("list", ArrayList.class);
            Long totalCount = page.getLong("totalCount");
            Integer pageIndex = page.getInt("pageIndex");
            Integer pageSize = page.getInt("pageSize");
            return new PageUtils(list, totalCount, pageIndex, pageSize);
        } else {
            log.error(resp.body());
            throw new EmosException("获取工作流数据异常");
        }
    }
}