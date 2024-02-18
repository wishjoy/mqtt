package com.project.collection.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.collection.dao.mapper.MeterElectricEnergyDayMapper;
import com.project.collection.dao.model.MeterElectricEnergyDay;
import com.project.collection.entity.MeterEnergyDayStatistics;
import com.project.collection.service.MeterElectricEnergyDayService;
import com.project.collection.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * 用电量按日统计(MeterElectricEnergyDay)表服务实现类
 *
 * @author makejava
 * @since 2024-01-12 18:01:09
 */
@Slf4j
@Service
public class MeterElectricEnergyDayServiceImpl extends ServiceImpl<MeterElectricEnergyDayMapper, MeterElectricEnergyDay> implements MeterElectricEnergyDayService {

    @Autowired
    private MeterElectricEnergyServiceImpl meterElectricEnergyService;
    //每天凌晨1点执行
    @Value("${scheduled.cron.meterElectricDay}")
    private String scheduledCron;
    @Scheduled(cron = "${scheduled.cron.meterElectricDay}")
    public void insertMeterElectricDay() {
        log.info("电表每日用电量统计定时任务开始执行...");
        List<MeterEnergyDayStatistics> list = meterElectricEnergyService.getMeterEnergyDayStatistics();
        List<MeterElectricEnergyDay> listDay = new LinkedList<>();
        list.forEach(meter->{
            MeterElectricEnergyDay meterElectricEnergyDay = new MeterElectricEnergyDay();
            meterElectricEnergyDay.setId(UUID.randomUUID().toString());
            meterElectricEnergyDay.setMeterSn(meter.getMeter());
            meterElectricEnergyDay.setValue(meter.getValue());
            meterElectricEnergyDay.setDate(TimeUtil.getStrYesterday(TimeUtil.getInstant()));
            meterElectricEnergyDay.setInsertTime(TimeUtil.getInstant());
            listDay.add(meterElectricEnergyDay);
        });
        this.saveBatch(listDay);
        log.info("电表每日用电量统计定时任务开执行执行结束...");
    }

}

