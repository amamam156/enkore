package com.hongchao.enkore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongchao.enkore.entity.OrderDetail;
import com.hongchao.enkore.mapper.OrderDetailMapper;
import com.hongchao.enkore.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService
{
}
