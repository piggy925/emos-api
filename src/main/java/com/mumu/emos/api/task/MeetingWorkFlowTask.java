package com.mumu.emos.api.task;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mumu.emos.api.db.dao.MeetingMapper;
import com.mumu.emos.api.db.dao.UserMapper;
import com.mumu.emos.api.exception.EmosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

@Slf4j
@Component
public class MeetingWorkFlowTask {
    @Resource
    private MeetingMapper meetingMapper;

    @Resource
    private UserMapper userMapper;

    @Value("${emos.receiveNotify}")
    private String receiveNotify;

    @Value("${emos.code}")
    private String code;

    @Value("${emos.tcode}")
    private String tcode;

    @Value("${workflow.url}")
    private String workflow;

    @Async("AsyncTaskExecutor")
    public void startMeetingWorkFlow(String uuid, int creatorId, String title, String date, String start, String meetingType) {
        HashMap info = userMapper.searchUserInfo(creatorId);

        JSONObject json = new JSONObject();
        json.set("url", receiveNotify);
        json.set("uuid", uuid);
        json.set("creatorId", creatorId);
        json.set("creatorName", info.get("name").toString());
        json.set("code", code);
        json.set("tcode", tcode);
        json.set("title", title);
        json.set("date", date);
        json.set("start", start);
        json.set("meetingType", meetingType);

        String[] roles = info.get("roles").toString().split("，");
        if (!ArrayUtil.contains(roles, "总经理")) {
            Integer managerId = userMapper.searchDeptManagerId(creatorId);
            json.set("managerId", managerId);

            Integer gmId = userMapper.searchGmId();
            json.set("gmId", gmId);

            // 查询参会人是否属于同一部门
            boolean sameDept = meetingMapper.searchMeetingMembersInSameDept(uuid);
            json.set("sameDept", sameDept);
        }
        String url = workflow + "/workflow/startMeetingProcess";
        HttpResponse resp = HttpRequest.post(url).header("Content-Type", "application/json").body(json.toString()).execute();
        if (resp.getStatus() == 200) {
            json = JSONUtil.parseObj(resp.body());
            String instanceId = json.getStr("instanceId");

            HashMap param = new HashMap();
            param.put("instanceId", instanceId);
            param.put("uuid", uuid);
            int rows = meetingMapper.updateMeetingInstanceId(param);
            if (rows != 1) {
                throw new EmosException("保存会议工作流实例失败");
            }
        } else {
            log.error(resp.body());
        }
    }
}