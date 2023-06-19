package com.shf.ssyx.order.receiver;

import com.rabbitmq.client.Channel;
import com.shf.ssyx.mq.constant.MqConst;
import com.shf.ssyx.order.service.OrderInfoService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
public class OrderReceiver {

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 订单支付成功，修改订单状态，扣减库存
     * @param orderNo
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_ORDER_PAY, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_PAY_DIRECT),
            key = {MqConst.ROUTING_PAY_SUCCESS}
    ))
    public void orderPay(String orderNo, Message message, Channel channel) throws IOException {

        if (StringUtils.isEmpty(orderNo)) {
            orderInfoService.orderPay(orderNo);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
