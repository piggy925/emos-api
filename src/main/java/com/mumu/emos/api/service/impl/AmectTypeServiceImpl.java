package com.mumu.emos.api.service.impl;

import com.mumu.emos.api.db.dao.AmectTypeMapper;
import com.mumu.emos.api.db.pojo.AmectType;
import com.mumu.emos.api.service.AmectTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

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
}