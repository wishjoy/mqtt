package com.project.jnzk.models.collection.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.jnzk.models.collection.dao.mapper.MeterElectricEnergyMapper;
import com.project.jnzk.models.collection.dao.model.MeterElectricEnergy;
import com.project.jnzk.models.collection.entity.MeterEnergyDayStatistics;
import com.project.jnzk.models.collection.service.MeterElectricEnergyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MeterElectricEnergyServiceImpl extends ServiceImpl<MeterElectricEnergyMapper, MeterElectricEnergy> implements MeterElectricEnergyService {

    @Autowired
    private MeterElectricEnergyMapper meterElectricEnergyMapper;


    public void testMeterElectricEnergyInsert(List<MeterElectricEnergy> list) {
        log.info("正泰电表元数据电表读数" + list);
        //计算增长值
        List<MeterElectricEnergy> insterList = new ArrayList<>();
        list.forEach(meterEl -> {
            MeterElectricEnergy meter = new MeterElectricEnergy();
            meter.setId(UUID.randomUUID().toString());
            meter.setCollectionId(meterEl.getCollectionId());
            meter.setGatewaySn(meterEl.getGatewaySn());
            meter.setGatewayTime(Instant.now().atZone(ZoneId.of("Asia/Shanghai")).toInstant());
            meter.setMeterSn(meterEl.getMeterSn());
            meter.setValue(meterEl.getValue());
            //获取上一次测点值
            QueryWrapper<MeterElectricEnergy> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("value")
                    .eq("meter_sn", meter.getMeterSn())
                    .orderByDesc("gateway_time")
                    .last("LIMIT 1");
            MeterElectricEnergy meterEle = this.getOne(queryWrapper);
            BigDecimal lastValue = (meterEle != null && meterEle.getValue() != null) ? meterEle.getValue() : BigDecimal.ZERO;
            if (lastValue.equals(BigDecimal.ZERO)) {
                meter.setUpValue(lastValue);
            } else {
                meter.setUpValue(meter.getValue().subtract(lastValue));
            }
            insterList.add(meter);
        });
        this.saveBatch(insterList);
    }

    public List<MeterEnergyDayStatistics> getMeterEnergyDayStatistics() {
        List<MeterEnergyDayStatistics> sumList = new ArrayList<>();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime startTime = LocalDateTime.of(yesterday.toLocalDate(), LocalDateTime.MIN.toLocalTime());
        LocalDateTime endTime = LocalDateTime.of(yesterday.toLocalDate(), LocalDateTime.MAX.toLocalTime());
        QueryWrapper<MeterElectricEnergy> queryWrapper = Wrappers.query();
        queryWrapper.select("meter_sn", "SUM(up_value) as value")
                .groupBy("meter_sn")
                .ge("gateway_time", startTime)
                .le("gateway_time", endTime);
        List<MeterElectricEnergy> meterElectricEnergies = meterElectricEnergyMapper.selectList(queryWrapper);
        meterElectricEnergies.forEach(meter -> {
            MeterEnergyDayStatistics meterEnergyDayStatistics = new MeterEnergyDayStatistics();
            meterEnergyDayStatistics.setMeter(meter.getMeterSn());
            meterEnergyDayStatistics.setValue(meter.getValue());
            sumList.add(meterEnergyDayStatistics);
        });
        return sumList;
    }
}
