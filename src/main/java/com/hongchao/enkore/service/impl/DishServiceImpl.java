package com.hongchao.enkore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongchao.enkore.common.CustomException;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService
{

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishMapper dishMapper;

    // save with flavor
    @Transactional
    public void saveWithFlavor(DishDto dishDto)
    {
        this.save(dishDto);
        Long dishId = dishDto.getId(); // dish id

        // dish flavor
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) ->
        {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    // get flavor and dish by id
    @Override
    public DishDto getByIdWithFlavor(Long id)
    {

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
    public void updateWithFlavor(DishDto dishDto)
    {
        // update dish
        this.updateById(dishDto);

        // clean
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        // insert
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) ->
        {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    @Transactional
    public void removeWithFlavor(List<Long> ids)
    {

        // check status
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, ids);
        queryWrapper.eq(Dish::getStatus, 1);

        int count = this.count(queryWrapper);
        // not delete
        if (count > 0)
        {
            throw new CustomException("Delete error: Dish is on sale!");
        }

        // delete dishes
        this.removeByIds(ids);

        // delete other
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(DishFlavor::getDishId, ids);
        log.info(lambdaQueryWrapper.toString());
        dishFlavorService.remove(lambdaQueryWrapper);

    }

    @Transactional
    public void stopStatus(String ids)
    {
        List<Long> idList = new ArrayList<>(Arrays.asList(ids.split(","))).stream().map(Long::parseLong)
                .collect(Collectors.toList());

        idList.forEach(id ->
        {
            Dish dish = dishMapper.selectById(id);
            dish.setStatus(0);
            dishMapper.updateById(dish);

        });
    }

    @Transactional
    public void startStatus(String ids)
    {
        List<Long> idList = new ArrayList<>(Arrays.asList(ids.split(","))).stream().map(Long::parseLong)
                .collect(Collectors.toList());

        idList.forEach(id ->
        {
            Dish dish = dishMapper.selectById(id);
                dish.setStatus(1);
                dishMapper.updateById(dish);

        });
    }

}