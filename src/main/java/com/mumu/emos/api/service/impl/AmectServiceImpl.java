package com.mumu.emos.api.service.impl;

import cn.hutool.core.map.MapUtil;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.dao.AmectMapper;
import com.mumu.emos.api.db.pojo.Amect;
import com.mumu.emos.api.service.AmectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author mumu
 * @date 2022/1/1
 */
@Slf4j
@Service
public class AmectServiceImpl implements AmectService {
    @Resource
    private AmectMapper amectMapper;

    @Override
    public PageUtils searchAmectByPage(HashMap param) {
        ArrayList<HashMap> list = amectMapper.searchAmectByPage(param);
        long totalCount = amectMapper.searchAmectCount(param);
        int start = MapUtil.getInt(param, "start");
        int length = MapUtil.getInt(param, "length");
        return new PageUtils(list, totalCount, start, length);
    }

    @Override
    @Transactional
    public int insert(ArrayList<Amect> amectList) {
        amectList.forEach(amect -> amectMapper.insert(amect));
        return amectList.size();
    }
}