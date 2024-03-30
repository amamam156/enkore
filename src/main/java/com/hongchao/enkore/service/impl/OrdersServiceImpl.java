package com.hongchao.enkore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongchao.enkore.common.BaseContext;
import com.hongchao.enkore.common.CustomException;
import com.hongchao.enkore.common.R;
import com.hongchao.enkore.entity.*;
import com.hongchao.enkore.mapper.OrdersMapper;
import com.hongchao.enkore.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService
{
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrdersService ordersService;


    @Transactional
    public void submit(Orders orders)
    {
        // gei id
        Long userId = BaseContext.getCurrentId();

        // check shopping cart
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(wrapper);

        if (shoppingCarts == null || shoppingCarts.size() == 0)
        {
            throw new CustomException("Order error: Cart is empty!");
        }
        // check user
        User user = userService.getById(userId);

        // check address book
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if (addressBook == null)
        {
            throw new CustomException("Order error: User address miss!");
        }

        Long ordersId = IdWorker.getId(); // order number

        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item) ->
        {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(ordersId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

        orders.setId(ordersId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//total price
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(ordersId));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName()) + (
                addressBook.getCityName() == null ? "" : addressBook.getCityName()) + (
                addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName()) + (
                addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        // insert to list order
        this.save(orders);
        // insert to list order detail
        orderDetailService.saveBatch(orderDetails);

        // clean shopping cart
        shoppingCartService.remove(wrapper);
    }

    @Transactional
    public void updateStatus(Orders orders){
        Long orderId = orders.getId();
        int status = orders.getStatus();
        log.info("Update status: {}", status, orderId);
        LambdaUpdateWrapper<Orders> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(Orders::getId, orderId);
        queryWrapper.set(Orders::getStatus, status);
        ordersService.update(queryWrapper);
    }

    public void orderAgain(Orders orders){
        Long orderId = orders.getId();
        Long userId = orders.getUserId();

        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getId, orderId);
        List<OrderDetail> orderDetails = orderDetailService.list(queryWrapper);
        List<ShoppingCart> shoppingCarts = orderDetails.stream().map((item) ->{

            ShoppingCart shoppingCart = new ShoppingCart();
            BeanUtils.copyProperties(item, shoppingCart);
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());
            return shoppingCart;
        }).collect(Collectors.toList());
        shoppingCartService.saveBatch(shoppingCarts);
    }

}


