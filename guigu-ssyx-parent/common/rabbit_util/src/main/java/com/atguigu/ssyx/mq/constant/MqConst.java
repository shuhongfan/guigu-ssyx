package com.atguigu.ssyx.mq.constant;

public class MqConst {
    /**
     * 消息补偿
     */
    public static final String MQ_KEY_PREFIX = "ssyx.mq:list";
    public static final int RETRY_COUNT = 3;

    /**
     * 商品上下架
     */
    public static final String EXCHANGE_GOODS_DIRECT = "ssyx.goods.direct";
    public static final String ROUTING_GOODS_UPPER = "ssyx.goods.upper";
    public static final String ROUTING_GOODS_LOWER = "ssyx.goods.lower";
    //队列
    public static final String QUEUE_GOODS_UPPER  = "ssyx.goods.upper";
    public static final String QUEUE_GOODS_LOWER  = "ssyx.goods.lower";

    /**
     * 团长上下线
     */
    public static final String EXCHANGE_LEADER_DIRECT = "ssyx.leader.direct";
    public static final String ROUTING_LEADER_UPPER = "ssyx.leader.upper";
    public static final String ROUTING_LEADER_LOWER = "ssyx.leader.lower";
    //队列
    public static final String QUEUE_LEADER_UPPER  = "ssyx.leader.upper";
    public static final String QUEUE_LEADER_LOWER  = "ssyx.leader.lower";

    //订单
    public static final String EXCHANGE_ORDER_DIRECT = "ssyx.order.direct";
    public static final String ROUTING_ROLLBACK_STOCK = "ssyx.rollback.stock";
    public static final String ROUTING_MINUS_STOCK = "ssyx.minus.stock";

    public static final String ROUTING_DELETE_CART = "ssyx.delete.cart";
    //解锁普通商品库存
    public static final String QUEUE_ROLLBACK_STOCK = "ssyx.rollback.stock";
    public static final String QUEUE_SECKILL_ROLLBACK_STOCK = "ssyx.seckill.rollback.stock";
    public static final String QUEUE_MINUS_STOCK = "ssyx.minus.stock";
    public static final String QUEUE_DELETE_CART = "ssyx.delete.cart";

    //支付
    public static final String EXCHANGE_PAY_DIRECT = "ssyx.pay.direct";
    public static final String ROUTING_PAY_SUCCESS = "ssyx.pay.success";
    public static final String QUEUE_ORDER_PAY  = "ssyx.order.pay";
    public static final String QUEUE_LEADER_BILL  = "ssyx.leader.bill";

    //取消订单
    public static final String EXCHANGE_CANCEL_ORDER_DIRECT = "ssyx.cancel.order.direct";
    public static final String ROUTING_CANCEL_ORDER = "ssyx.cancel.order";
    //延迟取消订单队列
    public static final String QUEUE_CANCEL_ORDER  = "ssyx.cancel.order";

    /**
     * 定时任务
     */
    public static final String EXCHANGE_DIRECT_TASK = "ssyx.exchange.direct.task";
    public static final String ROUTING_TASK_23 = "ssyx.task.23";
    //队列
    public static final String QUEUE_TASK_23  = "ssyx.queue.task.23";
}