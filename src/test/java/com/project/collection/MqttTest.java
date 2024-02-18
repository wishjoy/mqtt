//package com.project.collection;
//
//import com.project.collection.entity.BodyMoveMessage;
//import com.project.collection.utils.MqttConnect;
//import lombok.extern.slf4j.Slf4j;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.Instant;
//
//@SpringBootTest
//@Slf4j
//public class MqttTest {
//    @Value("${mqtt.broker}")
//    private String BROKER_URL; // MQTT 代理地址 // MQTT 代理地址
//    @Value("${mqtt.topic.electricity-meter}")
//    private String ELECTRICITY_METER; // 正泰电表
//    @Value("${mqtt.topic.body-move-message}")
//    private String BODY_MOVE_MESSAGE; //人体移动传感器
//
//    @Autowired
//    private MqttConnect mqttConnect;
//    @Test
//    public void test(){
//        try {
//            BodyMoveMessage bodyMoveMessage = new BodyMoveMessage();
//            bodyMoveMessage.setTime(Instant.now().toEpochMilli());
//            bodyMoveMessage.setGtewayId("111");
//            bodyMoveMessage.setDeviceId("6F90");
//            bodyMoveMessage.setType(1);
//            mqttConnect.mqttPub("testTopic", 2, "Hello, MQTT!");
//            String testTopic = mqttConnect.mqttSub("testTopic");
//            log.info(testTopic);
//        } catch (MqttException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
