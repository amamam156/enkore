package com.hongchao.enkore.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongchao.enkore.common.BaseContext;
import com.hongchao.enkore.common.R;
import com.hongchao.enkore.dto.OrdersDto;
import com.hongchao.enkore.entity.*;
import com.hongchao.enkore.service.OrderDetailService;
import com.hongchao.enkore.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController
{
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderDetailService orderDetailService;

    // user oder
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders)
    {

        log.info("Orders: {}", orders);
        ordersService.submit(orders);

        return R.success("Order successful!");
    }

    @GetMapping("/userPage")
    public R<Page> page(int page, int pageSize)
    {

        // get current id
        Long userId = BaseContext.getCurrentId();
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrdersDto> ordersDtoPage = new Page<>(page, pageSize);

        // create filter
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper();

        // Add filter criteria
        queryWrapper.eq(userId != null, Orders::getUserId, userId);

        // Add sorting criteria
        queryWrapper.orderByDesc(Orders::getOrderTime);
        ordersService.page(pageInfo, queryWrapper);
        // process search
        List<OrdersDto> list = pageInfo.getRecords().stream().map((item) -> {
            OrdersDto ordersDto = new OrdersDto();
            //get orderId search in orderDetail
            Long orderId = item.getId();
            LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OrderDetail::getOrderId, orderId);
            List<OrderDetail> details = orderDetailService.list(wrapper);
            BeanUtils.copyProperties(item, ordersDto);
            // set
            ordersDto.setOrderDetails(details);
            return ordersDto;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(pageInfo, ordersDtoPage, "records");
        ordersDtoPage.setRecords(list);
        // log
        log.info("list:{}", list);
        return R.success(ordersDtoPage);
    }


    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, Long number, String starTime, String endTime) {
        // get id
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrdersDto> ordersDtoPage = new Page<>(page, pageSize);
        // create filter
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        // add filter
        queryWrapper.orderByDesc(Orders::getOrderTime);
        // order number
        queryWrapper.eq(number != null, Orders::getId, number);
        // time
        queryWrapper.gt(!StringUtils.isEmpty(starTime), Orders::getOrderTime, starTime)
                .lt(!StringUtils.isEmpty(endTime), Orders::getOrderTime, endTime);
        ordersService.page(pageInfo, queryWrapper);
        List<OrdersDto> list = pageInfo.getRecords().stream().map((item) -> {
            OrdersDto ordersDto = new OrdersDto();
            // get orderId,get id，check in orderDetail
            Long orderId = item.getId();
            LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OrderDetail::getOrderId, orderId);
            List<OrderDetail> details = orderDetailService.list(wrapper);
            BeanUtils.copyProperties(item, ordersDto);
            // set
            ordersDto.setOrderDetails(details);
            return ordersDto;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(pageInfo, ordersDtoPage, "records");
        ordersDtoPage.setRecords(list);
        // log
        log.info("list:{}", list);
        return R.success(ordersDtoPage);
    }

    @PutMapping
    public R<String> updateStatus(@RequestBody Orders orders){

        log.info("update status: {}", orders);
        ordersService.updateStatus(orders);
        return R.success("update status successful");
    }

    @PostMapping("/again")
    public R<String> orderAgain(@RequestBody Orders orders){
        log.info("order again: {}", orders);
        ordersService.orderAgain(orders);
        return R.success("order again succesful");
    }

}
