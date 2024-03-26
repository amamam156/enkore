package com.hongchao.enkore.controller;

// meal management

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongchao.enkore.common.R;
import com.hongchao.enkore.dto.SetmealDto;
import com.hongchao.enkore.entity.Category;
import com.hongchao.enkore.entity.Setmeal;
import com.hongchao.enkore.service.CategoryService;
import com.hongchao.enkore.service.SetmealDishService;
import com.hongchao.enkore.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController
{

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    // add meal
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto)
    {
        log.info("Meal: {}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("Add meal successful!");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name)
    {
        // create object
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        // condition creator
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        // add condition
        queryWrapper.like(name != null, Setmeal::getName, name);

        // add sort
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, queryWrapper);

        // object copy
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");

        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) ->
        {
            SetmealDto setmealDto = new SetmealDto();

            BeanUtils.copyProperties(item, setmealDto);

            Long categoryId = item.getCategoryId(); // category id

            Category category = categoryService.getById(categoryId);

            if (category != null)
            {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);

        return R.success(dtoPage);
    }

    // delete meal
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids)
    {
        log.info("ids, {}", ids);
        setmealService.removeWithDish(ids);
        return R.success("Delete meal successful!");
    }

    @PostMapping("/status/0")
    public R<String> stopStatus(String ids)
    {
        log.info("ids, {}", ids);

        setmealService.stopStatus(ids);

        return R.success("Stop successful!");
    }

    @PostMapping("/status/1")
    public R<String> startStatus(String ids)
    {
        log.info("ids, {}", ids);

        setmealService.startStatus(ids);

        return R.success("Start successful!");
    }
}
