package com.mumu.emos.api.service.impl;

import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.dao.AmectTypeMapper;
import com.mumu.emos.api.db.pojo.AmectType;
import com.mumu.emos.api.service.AmectTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author mumu
 * @date 2022/1/1
 */
@Service
public class AmectTypeServiceImpl implements AmectTypeService {
    @Resource
    private AmectTypeMapper amectTypeMapper;

    @Override
    public ArrayList<AmectType> searchAllAmectType() {
        return amectTypeMapper.searchAllAmectType();
    }

    @Override
    public PageUtils searchAmectTypeByPage(HashMap param) {
        ArrayList<HashMap> list = amectTypeMapper.searchAmectTypeByPage(param);

        long count = amectTypeMapper.searchAmectTypeCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list, count, start, length);
        return pageUtils;
    }
}