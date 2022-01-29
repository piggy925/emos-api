package com.mumu.emos.api.task;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mumu.emos.api.db.dao.LeaveMapper;
import com.mumu.emos.api.db.dao.UserMapper;
import com.mumu.emos.api.exception.EmosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author mumu
 * @date 2022/1/29
 */
@Component
@Slf4j
public class LeaveWorkflowTask {
    @Value("${emos.code}")
    private String code;

    @Value("${emos.tcode}")
    private String tcode;

    @Value("${workflow.url}")
    private String workflow;

    @Value("${emos.recieveNotify}")
    private String recieveNotify;

    @Resource
    private UserMapper userMapper;

    @Resource
    private LeaveMapper leaveMapper;

    @Async("AsyncTaskExecutor")
    public void startLeaveWorkflow(int id, int creatorId, String days) {
        HashMap info = userMapper.searchUserInfo(creatorId);
        JSONObject json = new JSONObject();
        json.set("url", recieveNotify);
        json.set("creatorId", creatorId);
        json.set("creatorName", info.get("name").toString());
        json.set("code", code);
        json.set("tcode", tcode);
        json.set("title", info.get("dept").toString() + info.get("name").toString() + "的请假");
        Integer managerId = userMapper.searchDeptManagerId(creatorId);
        json.set("managerId", managerId);
        Integer gmId = userMapper.searchGmId();
        json.set("gmId", gmId);
        json.set("days", Double.parseDouble(days));

        String url = workflow + "/workflow/startLeaveProcess";
        HttpResponse resp = HttpRequest.post(url).header("Content-Type", "application/json")
                .body(json.toString()).execute();
        if (resp.getStatus() == 200) {
            json = JSONUtil.parseObj(resp.body());
            String instanceId = json.getStr("instanceId");
            HashMap param = new HashMap();
            param.put("id", id);
            param.put("instanceId", instanceId);
            int row = leaveMapper.updateLeaveInstanceId(param);
            if (row != 1) {
                throw new EmosException("保存请假工作流实例ID失败");
            }
        } else {
            log.error(resp.body());
        }
    }
}