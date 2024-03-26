package com.hongchao.enkore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongchao.enkore.common.CustomException;
import com.hongchao.enkore.entity.Category;
import com.hongchao.enkore.entity.Dish;
import com.hongchao.enkore.entity.Setmeal;
import com.hongchao.enkore.mapper.CategoryMapper;
import com.hongchao.enkore.service.CategoryService;
import com.hongchao.enkore.service.DishService;
import com.hongchao.enkore.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService
{

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    // delete by id
    @Override
    public void remove(Long id)
    {

        // food

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // search by id
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        // check relative
        if (count > 0)
        {
            // relative
            throw new CustomException("Deletion error: The current category has related dishes and cannot be deleted!");
        }

        // meal

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // search by id
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        // check relative
        if (count2 > 0)
        {
            // relative
            throw new CustomException("Deletion error: The current category has related meals and cannot be deleted!");
        }
        // delete
        super.removeById(id);

    }
}
