package com.hongchao.enkore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongchao.enkore.dto.DishDto;
import com.hongchao.enkore.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish>
{

    // add flavor
    public void saveWithFlavor(DishDto dishDto);

    // get flavor and dish by id
    public DishDto getByIdWithFlavor(Long id);

    // update with flavor
    public void updateWithFlavor(DishDto dishDto);

    // remove with flavor
    public void removeWithFlavor(List<Long> ids);

    // stop status
    public void stopStatus(String ids);

    // start status
    public void startStatus(String ids);
}
