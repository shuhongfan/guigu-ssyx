package com.shf.ssyx.cart.receiver;

import com.rabbitmq.client.Channel;
import com.shf.ssyx.cart.service.CartInfoService;
import com.shf.ssyx.mq.constant.MqConst;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CartReceiver {
    @Autowired
    private CartInfoService cartInfoService;

    /**
     * 根据userId删除用户选中购物车记录
     *
     * @param userId
     * @param message
     * @param channel
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_DELETE_CART, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_ORDER_DIRECT),
            key = {MqConst.ROUTING_DELETE_CART}))
    public void deleteCart(Long userId, Message message, Channel channel) throws IOException {
        if (userId != null) {
            cartInfoService.deleteCartCheck(userId);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
