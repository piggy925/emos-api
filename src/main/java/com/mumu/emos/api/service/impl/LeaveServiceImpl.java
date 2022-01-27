package com.mumu.emos.api.service.impl;

import cn.hutool.core.map.MapUtil;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.dao.LeaveMapper;
import com.mumu.emos.api.service.LeaveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author mumu
 * @date 2022/1/27
 */
@Service
public class LeaveServiceImpl implements LeaveService {
    @Resource
    private LeaveMapper leaveMapper;

    @Override
    public PageUtils searchLeaveByPage(HashMap param) {
        ArrayList<HashMap> list = leaveMapper.searchLeaveByPage(param);
        long count = leaveMapper.searchLeaveCount(param);
        int start = MapUtil.getInt(param, "start");
        int length = MapUtil.getInt(param, "length");
        return new PageUtils(list, count, start, length);
    }
}