package com.project.jnzk.mqtt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.project.jnzk.models.collection.dao.model.MeterElectricEnergy;

import com.project.jnzk.models.collection.entity.DataItem;
import com.project.jnzk.models.collection.entity.DateType;
import com.project.jnzk.models.collection.entity.Heart;
import com.project.jnzk.models.collection.service.MeterElectricEnergyService;
import com.project.jnzk.models.collection.service.impl.MeterCollectionServiceImpl;
import com.project.jnzk.models.collection.service.impl.MeterElectricEnergyServiceImpl;
import com.project.jnzk.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class MqttSubscriber {
    @Autowired
    private RedisUtil redisUtil;


    @Autowired
    private MeterElectricEnergyService meterElectricEnergyService;

    @Autowired
    private MeterCollectionServiceImpl meterCollectionService;

    @Autowired
    private MeterElectricEnergyServiceImpl meterElectricEnergyServiceImpl;

    private static final String BROKER_URL = "tcp://47.99.157.56:1883"; // MQTT 代理地址
    private static final String TOPIC = "/dingiiot/linkqi/pub/+"; // 订阅的主题

    public MqttSubscriber() {
        try {
            MqttClient client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            client.connect(options);
            // 订阅主题
            client.subscribe(TOPIC, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    // 处理接收到的消息
                    try {
                        String payload = new String(message.getPayload());
                        log.info("Received message from topic " + topic + ": " + payload);
                        //JSONArray json = (JSONArray) JSONObject.parse(payload);
                        JSONObject json = (JSONObject) JSONObject.parse(payload);
                        DateType dateType = DateType.valueOf(json.get("type").toString().toUpperCase());

                        switch (dateType) {
                            case HEART:
                                Heart heart = JSONObject.parseObject(payload, Heart.class);
                                //将心跳放进redis,10分钟过期
                                redisUtil.setEx("heart", payload, 10, TimeUnit.MINUTES);
                                break;
                            case REAL:
                                meterInsertDb(json);
                                break;
                        }
                    } catch (Exception e) {
                        log.error("报错信息" + e);
                    }
                }
            });
            // 设置回调，处理连接丢失等情况
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    // 已在订阅时处理，此处不需要再处理
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // 消息发送完成后的回调
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 正泰上报电表源数据入库
     *
     * @param json
     */
    private void meterInsertDb(JSONObject json) {
        log.info("正泰电表元数据处理" + json);
        String datas = json.get("data").toString();
        JSONObject parse = JSON.parseObject(datas);
        Map<String, Object> map = parse.getInnerMap();
        List<DataItem> dataItemList = new ArrayList<>();
        map.forEach((k, v) -> {
            System.out.println(k);
            System.out.println(v);
            if (v instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) v;
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject itemObject = jsonArray.getJSONObject(i);
                    DataItem dataItem = itemObject.toJavaObject(DataItem.class);
                    dataItemList.add(dataItem);
                }
            }
        });
        String gateWaySn = json.get("sn").toString();
        List<MeterElectricEnergy> meterElectricEnergyList = meterCollectionService.doMeterInsertDb(dataItemList, gateWaySn);
        meterElectricEnergyServiceImpl.testMeterElectricEnergyInsert(meterElectricEnergyList);
    }

}
