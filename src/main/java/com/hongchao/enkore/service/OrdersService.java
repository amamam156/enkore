package com.hongchao.enkore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongchao.enkore.entity.Orders;

public interface OrdersService extends IService<Orders>
{
    // user order
    public void submit(Orders orders);
}
