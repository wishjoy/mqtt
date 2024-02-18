package com.project.collection;


import com.project.collection.utils.MqttConnect;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MqttConnectTest {


    @Autowired
    private MqttConnect mqttConnect;

    @Test
    public void testMqttPubAndSubWithQoS() throws MqttException, InterruptedException {
        // 在此处使用 MqttConnect 进行其他测试，例如测试订阅和发布消息的质量级别
        mqttConnect.mqttSub("testTopic");

    }

}
