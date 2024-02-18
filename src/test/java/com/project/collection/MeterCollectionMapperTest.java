package com.project.collection;

import com.project.collection.dao.mapper.MeterCollectionMapper;
import com.project.collection.dao.model.MeterCollection;
import com.project.collection.dao.model.MeterElectricEnergy;
import com.project.collection.dao.model.MeterElectricEnergyDay;
import com.project.collection.entity.MeterEnergyDayStatistics;
import com.project.collection.service.impl.MeterElectricEnergyDayServiceImpl;
import com.project.collection.service.impl.MeterElectricEnergyServiceImpl;
import com.project.collection.utils.MqttConnect;
import com.project.collection.utils.TimeUtil;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class MeterCollectionMapperTest {

    @Autowired
    private MeterCollectionMapper meterCollectionMapper;

    @Autowired
    private MeterElectricEnergyServiceImpl meterElectricEnergyService;

    @Autowired
    private MeterElectricEnergyDayServiceImpl meterElectricEnergyDayService;


    @Autowired
    private MqttConnect mqttConnect;

    @Test
    public void testInsert() throws MqttException {
        // Create a MeterCollection object to insert into the database
        MeterCollection meterCollection = new MeterCollection();
        meterCollection.setType("real");
        meterCollection.setGatewaySn("GW001");
        meterCollection.setGatewayTime(TimeUtil.getInstant());
        meterCollection.setMeterSn("Meter001");
        meterCollection.setPointId("Tag1");
        meterCollection.setPointQuality(1);
        meterCollection.setPointValue(new BigDecimal("10.5"));

        // Insert the MeterCollection object into the database
        meterCollectionMapper.insert(meterCollection);

        // Check if the ID was generated after the insert
        System.out.println("Inserted MeterCollection ID: " + meterCollection.getId());
    }
    @Test
    public void testMeterElectricEnergyInsert() {
        MeterElectricEnergy meter = new MeterElectricEnergy();
        meter.setId(UUID.randomUUID().toString());
        meter.setCollectionId("123");
        meter.setGatewaySn("GW001");
       // meter.setGatewayTime(new Timestamp(System.currentTimeMillis()));
        meter.setMeterSn("M001");
        meter.setValue(BigDecimal.valueOf(10.5));
        meter.setUpValue(BigDecimal.valueOf(2.3));
        boolean result = meterElectricEnergyService.save(meter);
        assert(result == true);
    }
    @Test
    public void insertTestData() {
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
        meterElectricEnergyDayService.saveBatch(listDay);
    }
    @Test
    public void test(){
        meterElectricEnergyService.getMeterEnergyDayStatistics();
    }

}

