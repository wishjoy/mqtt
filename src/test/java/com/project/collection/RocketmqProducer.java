package com.project.collection;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
public class RocketmqProducer {
    private static final Logger logger = LoggerFactory.getLogger(RocketmqProducer.class);
    // RocketMQ配置
    private static final String NAME_SERVER_ADDR = "47.99.94.249:9876";
    private static final String PRODUCER_GROUP = "your_producer_group";
    private static final String CONSUMER_GROUP = "your_consumer_group";
    private static final String TOPIC = "joy_topic";
    @Test
    public void rockMqPush() {
        try {
            DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
            producer.setNamesrvAddr(NAME_SERVER_ADDR);
            producer.start();
            // 创建消息实例，参数分别是：主题，消息体
            Message message = new Message(TOPIC, "Hello, joy!".getBytes());
            // 发送消息并获取发送结果
            SendResult sendResult = producer.send(message);

            if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
                logger.info("消息发送成功，消息ID：{}", sendResult.getMsgId());
            } else {
                logger.error("消息发送失败，错误信息：{}", sendResult.getSendStatus());
            }
        } catch (Exception e) {
            logger.error("消息发送异常", e);
        }
    }
    @Test
    public void rocketmqSub() {
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
            consumer.setNamesrvAddr(NAME_SERVER_ADDR);
            consumer.subscribe(TOPIC, "*");
            // 注册消息监听器，处理消息的业务逻辑
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(
                        java.util.List<MessageExt> msgs,
                        ConsumeConcurrentlyContext context) {
                    for (MessageExt msg : msgs) {
                        logger.info("收到消息：{}", new String(msg.getBody()));
                    }
                    // 消费成功
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            // 启动消费者
            consumer.start();
            logger.info("消费者启动成功...");
            // 保持进程不退出，监听消息
            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            logger.error("消费者异常", e);
        }
    }
}
