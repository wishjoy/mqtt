package com.project.collection;


import com.project.collection.utils.MqttConnect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MqttApplicationTests {


    @Autowired
    private MqttConnect mqttConnect;

    @Test
    void contextLoads() {
        System.out.println("11111");
    }

}
