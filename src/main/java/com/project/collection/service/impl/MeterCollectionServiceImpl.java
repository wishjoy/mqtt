package com.project.collection.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.collection.dao.mapper.MeterCollectionMapper;
import com.project.collection.dao.model.MeterCollection;
import com.project.collection.dao.model.MeterElectricEnergy;
import com.project.collection.entity.DataItem;
import com.project.collection.service.MeterCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

@Service
@Slf4j
public class MeterCollectionServiceImpl extends ServiceImpl<MeterCollectionMapper,MeterCollection> implements MeterCollectionService {

    @Autowired
    private MeterCollectionMapper meterCollectionMapper;

    public void insertMeterCollection(MeterCollection meterCollection) {
        meterCollectionMapper.insert(meterCollection);
    }

    public void deleteMeterCollectionById(Long id) {
        meterCollectionMapper.deleteById(id);
    }

    public void updateMeterCollection(MeterCollection meterCollection) {
        meterCollectionMapper.updateById(meterCollection);
    }

    public MeterCollection getMeterCollectionById(Long id) {
        return meterCollectionMapper.selectById(id);
    }


    public List<MeterElectricEnergy> doMeterInsertDb(List<DataItem> dataItemList, String gateWaySn) {
        log.info("正泰电表元数据入库"+dataItemList);
        List<MeterElectricEnergy> meterElectricEnergyList = new ArrayList<>();
        Optional.ofNullable(dataItemList).ifPresent(dataItems -> {
            List<MeterCollection> meterCollectionList = new ArrayList<>();
            dataItemList.forEach(dataItem -> {
                MeterCollection meterCollection = new MeterCollection();
                meterCollection.setId(UUID.randomUUID().toString());
                meterCollection.setType("real");
                meterCollection.setGatewaySn(gateWaySn);
                meterCollection.setGatewayTime(Instant.now().atZone(ZoneId.of("Asia/Shanghai")).toInstant());
                //电表相关
                String[] strArray = StringUtils.split(dataItem.getId(), "_");
                meterCollection.setMeterSn(strArray[0]);
                meterCollection.setPointId(strArray[1]);
                meterCollection.setPointQuality(Integer.parseInt(dataItem.getQuality()));
                meterCollection.setPointValue(BigDecimal.valueOf(Double.valueOf(dataItem.getValue())));
                if ("Ep".equals(strArray[1])) {
                    MeterElectricEnergy meter = new MeterElectricEnergy();
                    meter.setId(UUID.randomUUID().toString());
                    meter.setCollectionId(meterCollection.getId());
                    meter.setGatewaySn(gateWaySn);
                    meter.setGatewayTime(Instant.now().atZone(ZoneId.of("Asia/Shanghai")).toInstant());
                    meter.setMeterSn(meterCollection.getMeterSn());
                    meter.setValue(meterCollection.getPointValue());
                    meterElectricEnergyList.add(meter);
                }
                meterCollectionList.add(meterCollection);
            });
            this.saveBatch(meterCollectionList);
        });
        return meterElectricEnergyList;
    }


}

