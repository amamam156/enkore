package com.hongchao.enkore.controller;

// dish management

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongchao.enkore.common.R;
import com.hongchao.enkore.dto.DishDto;
import com.hongchao.enkore.entity.Category;
import com.hongchao.enkore.entity.Dish;
import com.hongchao.enkore.entity.DishFlavor;
import com.hongchao.enkore.service.CategoryService;
import com.hongchao.enkore.service.DishFlavorService;
import com.hongchao.enkore.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController
{

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto)
    {
        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);

        return R.success("Add dish successful!");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name)
    {
        // create object
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        // condition creator
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        // add condition
        queryWrapper.like(name != null, Dish::getName, name);

        // add sort
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        dishService.page(pageInfo, queryWrapper);

        // object copy
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) ->
        {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId(); // category id

            Category category = categoryService.getById(categoryId);

            if (category != null)
            {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    // get flavor and dish by id
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id)
    {

        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }

    // update
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto)
    {
        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);

        return R.success("Add dish successful!");
    }

    // get list dish
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish)
    {

        // create condition
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        // status
        queryWrapper.eq(Dish::getStatus, 1);
        // sort
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> dishDtoList = list.stream().map((item) ->
        {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId(); // category id

            Category category = categoryService.getById(categoryId);

            if (category != null)
            {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            //
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);

            List<DishFlavor> dishFlavorsList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorsList);

            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }

    // get list dish


    /*
    @GetMapping("/list")

    public R<List<Dish>> list(Dish dish)
    {

        // create condition
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        // status
        queryWrapper.eq(Dish::getStatus, 1);
        // sort
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        return R.success(list);
    }

     */


    // delete dish

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids)
    {
        dishService.removeWithFlavor(ids);
        return R.success("Delete successful!");
    }

    @PostMapping("/status/0")
    public R<String> stopStatus(String ids)
    {
        log.info("ids, {}", ids);

        dishService.stopStatus(ids);

        return R.success("Stop successful!");
    }

    @PostMapping("/status/1")
    public R<String> startStatus(String ids)
    {
        log.info("ids, {}", ids);

        dishService.startStatus(ids);

        return R.success("Start successful!");
    }
}
