package com.hongchao.enkore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongchao.enkore.entity.Dish;
import com.hongchao.enkore.mapper.DishMapper;
import com.hongchao.enkore.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
