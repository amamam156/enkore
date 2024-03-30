package com.hongchao.enkore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongchao.enkore.common.CustomException;
import com.hongchao.enkore.common.R;
import com.hongchao.enkore.dto.SetmealDto;
import com.hongchao.enkore.entity.Dish;
import com.hongchao.enkore.entity.Setmeal;
import com.hongchao.enkore.entity.SetmealDish;
import com.hongchao.enkore.mapper.SetmealMapper;
import com.hongchao.enkore.service.SetmealDishService;
import com.hongchao.enkore.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.compiler.ast.Stmnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService
{

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealMapper setmealMapper;

    // save meal with dish
    @Transactional
    public void saveWithDish(SetmealDto setmealDto)
    {
        // set meal
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) ->
        {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        // save with dish
        setmealDishService.saveBatch(setmealDishes);

    }

    // delete meal
    @Transactional
    public void removeWithDish(List<Long> ids)
    {

        // check status
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);

        int count = this.count(queryWrapper);
        // not delete
        if (count > 0)
        {
            throw new CustomException("Delete error: Meal is on sale!");
        }

        // delete meal
        this.removeByIds(ids);

        // delete other
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(lambdaQueryWrapper);

    }

    @Transactional
    public void stopStatus(String ids)
    {
        List<Long> idList = new ArrayList<>(Arrays.asList(ids.split(","))).stream().map(Long::parseLong)
                .collect(Collectors.toList());

        idList.forEach(id ->
        {
            Setmeal meal = setmealMapper.selectById(id);
            meal.setStatus(0);
            setmealMapper.updateById(meal);

        });
    }

    @Transactional
    public void startStatus(String ids)
    {
        List<Long> idList = new ArrayList<>(Arrays.asList(ids.split(","))).stream().map(Long::parseLong)
                .collect(Collectors.toList());

        idList.forEach(id ->
        {
            Setmeal meal = setmealMapper.selectById(id);
            meal.setStatus(1);
            setmealMapper.updateById(meal);

        });
    }

    @Transactional
    public void updateWithDish(SetmealDto setmealDto)
    {
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        Long setmealId = setmealDto.getId();
        // delete
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmealId);
        setmealDishService.remove(queryWrapper);
        // re-add
        setmealDishes = setmealDishes.stream().map((item) ->{
            //这属性没有，需要我们手动设置一下
            item.setSetmealId(setmealId);
            return item;
        }).collect(Collectors.toList());
        // update
        setmealService.updateById(setmealDto);
        // update dishes
        setmealDishService.saveBatch(setmealDishes);
    }
}
