package com.project.jnzk.utils;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MqttConnect {

    @Value("${mqtt.broker}")
    private String BROKER_URL; // MQTT 代理地址

    private MqttClient mqttClient;

    //public List messageInfo;

    public MqttClient getMqttClient() throws MqttException {
        if (mqttClient == null || !mqttClient.isConnected()) {
            mqttClient = new MqttClient(BROKER_URL, MqttClient.generateClientId());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            mqttClient.connect(options);
        }
        return mqttClient;
    }

    /**
     * 发布消息
     *
     * @param topic
     * @param qos
     * @param mes
     * @throws MqttException
     */
    public void mqttPub(String topic, int qos, String mes) throws MqttException {
        MqttMessage message = new MqttMessage(mes.getBytes());
        message.setQos(qos);
        getMqttClient().publish(topic, message);
        disconnect();
    }

    /**
     * 订阅消息
     *
     * @param topic
     * @return
     * @throws MqttException
     */
    public String mqttSub(String topic) throws MqttException {
        List list = new ArrayList();
        list.set(0,"111");
        // 订阅主题
        getMqttClient().subscribe(topic, new IMqttMessageListener() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                // 处理接收到的消息
                String payload = new String(message.getPayload());
                System.out.println("Received message from topic " + topic + ": " + payload);
                list.set(0,payload);
               // System.out.println(list.get(1));
                disconnect();
            }

        });
        return list.get(0).toString();
    }




    public void disconnect() throws MqttException {
        // 断开连接
        if (mqttClient != null && mqttClient.isConnected()) {
            mqttClient.disconnect();
        }
    }
}







