package com.shf.ssyx.order.service.impl;


import com.shf.ssyx.model.order.OrderItem;
import com.shf.ssyx.order.mapper.OrderItemMapper;
import com.shf.ssyx.order.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单项信息 服务实现类
 * </p>
 *
 * @author shf
 * @since 2023-06-18
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
