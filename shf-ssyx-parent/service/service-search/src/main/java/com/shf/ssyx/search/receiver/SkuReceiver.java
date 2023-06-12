package com.shf.ssyx.search.receiver;


import com.rabbitmq.client.Channel;
import com.shf.ssyx.mq.constant.MqConst;
import com.shf.ssyx.mq.service.RabbitService;
import com.shf.ssyx.search.service.SkuService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SkuReceiver {
    @Autowired
    private SkuService skuService;

    /**
     * 接收端  商品上架
     * @param skuId
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_GOODS_UPPER, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_GOODS_DIRECT),
            key = {MqConst.ROUTING_GOODS_UPPER}))
    public void upperSku(Long skuId, Message message, Channel channel) throws IOException {
        if (skuId != null) {
//            调用方法商品上架
            skuService.upperSku(skuId);
        }
//        消息手动确认
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_GOODS_LOWER, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_GOODS_DIRECT),
            key = {MqConst.ROUTING_GOODS_LOWER}))
    public void lowerSku(Long skuID, Message message, Channel channel) throws IOException {
        if (skuID != null) {
//            调用方法商品上架
            skuService.lowerSku(skuID);
        }
//        消息手动确认
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
