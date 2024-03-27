package com.hongchao.enkore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongchao.enkore.entity.ShoppingCart;
import com.hongchao.enkore.mapper.ShoppingCartMapper;
import com.hongchao.enkore.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCarServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService
{
}
