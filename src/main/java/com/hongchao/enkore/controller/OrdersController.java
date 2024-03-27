package com.hongchao.enkore.controller;

import com.hongchao.enkore.common.R;
import com.hongchao.enkore.entity.Orders;
import com.hongchao.enkore.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController
{
    @Autowired
    private OrdersService ordersService;


    // user oder
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){

        log.info("Orders: {}", orders);
        ordersService.submit(orders);

        return R.success("Order successful!");
    }
}
