package com.hongchao.enkore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongchao.enkore.dto.DishDto;
import com.hongchao.enkore.entity.Dish;
import com.hongchao.enkore.entity.DishFlavor;
import com.hongchao.enkore.mapper.DishMapper;
import com.hongchao.enkore.service.DishFlavorService;
import com.hongchao.enkore.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {


    @Autowired
    private DishFlavorService dishFlavorService;


    // save with flavor
    @Transactional
    public void saveWithFlavor(DishDto dishDto){
        this.save(dishDto);
        Long dishId = dishDto.getId(); // dish id

        // dish flavor
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());


        dishFlavorService.saveBatch(flavors);
    }


    // get flavor and dish by id
    @Override
    public DishDto getByIdWithFlavor(Long id) {

        // get dish
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        // get flavor by dish
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);


        return dishDto;
    }


    // update with flavor
    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        // update dish
        this.updateById(dishDto);

        // clean
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        // insert
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());


        dishFlavorService.saveBatch(flavors);
    }
}
