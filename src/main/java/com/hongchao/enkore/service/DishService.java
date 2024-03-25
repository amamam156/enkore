package com.hongchao.enkore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongchao.enkore.dto.DishDto;
import com.hongchao.enkore.entity.Dish;

public interface DishService extends IService<Dish> {

    // add flavor
    public void saveWithFlavor(DishDto dishDto);

    // get flavor and dish by id
    public DishDto getByIdWithFlavor(Long id);

    // update with flavor
    public void updateWithFlavor(DishDto dishDto);

}
