package com.hongchao.enkore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongchao.enkore.common.CustomException;
import com.hongchao.enkore.dto.SetmealDto;
import com.hongchao.enkore.entity.Setmeal;
import com.hongchao.enkore.entity.SetmealDish;
import com.hongchao.enkore.mapper.SetmealMapper;
import com.hongchao.enkore.service.SetmealDishService;
import com.hongchao.enkore.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    // save meal with dish
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        // set meal
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId() );
            return item;
        }).collect(Collectors.toList());


        // save with dish
        setmealDishService.saveBatch(setmealDishes);


    }

    // delete meal
    @Transactional
    public void removeWithDish(List<Long> ids) {


        // check status
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);

        int count = this.count(queryWrapper);
        // not delete
        if (count > 0){
            throw new CustomException("Delete error: Meal is on sale!");
        }

        // delete meal
        this.removeByIds(ids);

        // delete other
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getId, ids);
        setmealDishService.remove(lambdaQueryWrapper);

    }
}
