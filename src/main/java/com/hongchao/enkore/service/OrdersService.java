package com.hongchao.enkore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongchao.enkore.entity.Orders;

public interface OrdersService extends IService<Orders>
{
    // user order
    public void submit(Orders orders);

    // update status
    public  void updateStatus(Orders orders);

    // order again
    public void orderAgain(Orders orders);
}
