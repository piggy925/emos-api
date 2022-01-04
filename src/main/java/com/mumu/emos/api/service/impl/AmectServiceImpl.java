package com.mumu.emos.api.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.mumu.emos.api.common.util.PageUtils;
import com.mumu.emos.api.db.dao.AmectMapper;
import com.mumu.emos.api.db.pojo.Amect;
import com.mumu.emos.api.exception.EmosException;
import com.mumu.emos.api.service.AmectService;
import com.mumu.emos.api.wxpay.MyWXPayConfig;
import com.mumu.emos.api.wxpay.WXPay;
import com.mumu.emos.api.wxpay.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mumu
 * @date 2022/1/1
 */
@Slf4j
@Service
public class AmectServiceImpl implements AmectService {
    @Resource
    private AmectMapper amectMapper;
    @Resource
    private MyWXPayConfig myWXPayConfig;

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

    @Override
    public HashMap searchById(int id) {
        return amectMapper.searchById(id);
    }

    @Override
    public int update(HashMap param) {
        return amectMapper.update(param);
    }

    @Override
    public int deleteAmectByIds(Integer[] ids) {
        return amectMapper.deleteAmectByIds(ids);
    }

    @Override
    public String createNativeAmectPayOrder(HashMap param) {
        int amectId = MapUtil.getInt(param, "amectId");
        HashMap map = amectMapper.selectAmectByCondition(param);
        if (map != null && map.size() > 0) {
            // 将支付金额的单位转为分
            String amount = new BigDecimal(MapUtil.getStr(map, "amount")).multiply(new BigDecimal(100)).intValue() + "";
            try {
                WXPay wxPay = new WXPay(myWXPayConfig);
                HashMap<String, String> orderMap = new HashMap<>();
                orderMap.put("nonce_str", WXPayUtil.generateNonceStr()); //随机字符串
                orderMap.put("body", "缴纳罚款");
                orderMap.put("out_trade_no", MapUtil.getStr(map, "uuid"));
                orderMap.put("total_fee", amount);
                orderMap.put("spbill_create_ip", "127.0.0.1");
                orderMap.put("notify_url", "http://s10.z100.vip:22679/emos-api/amect/receiveMessage");
                orderMap.put("trade_type", "NATIVE");

                String sign = WXPayUtil.generateSignature(orderMap, myWXPayConfig.getKey());
                orderMap.put("sign", sign);

                Map<String, String> result = wxPay.unifiedOrder(orderMap);
                String prepayId = result.get("prepay_id");
                String codeUrl = result.get("code_url");
                if (prepayId != null) {
                    HashMap payMap = new HashMap<>();
                    payMap.put("prepayId", prepayId);
                    payMap.put("amectId", amectId);
                    int rows = amectMapper.updatePrepayId(payMap);
                    if (rows != 1) {
                        throw new EmosException("更新罚款支付订单ID失败");
                    }
                    // 将罚款支付url转换为图片
                    QrConfig qrConfig = new QrConfig();
                    qrConfig.setWidth(255);
                    qrConfig.setHeight(255);
                    qrConfig.setMargin(2);
                    String qrCodeBase64 = QrCodeUtil.generateAsBase64(codeUrl, qrConfig, "jpg");
                    return qrCodeBase64;
                } else {
                    log.error("创建支付订单失败", result);
                    throw new EmosException("创建支付订单失败");
                }
            } catch (Exception e) {
                log.error("创建支付订单失败", e);
                throw new EmosException("创建支付订单失败");
            }
        } else {
            throw new EmosException("没有找到罚款单");
        }
    }

    @Override
    public int updateStatus(HashMap param) {
        return amectMapper.updateStatus(param);
    }
}