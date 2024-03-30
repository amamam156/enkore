package com.hongchao.enkore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongchao.enkore.dto.SetmealDto;
import com.hongchao.enkore.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal>
{

    // save meal with dish
    public void saveWithDish(SetmealDto setmealDto);

    // delete meal
    public void removeWithDish(List<Long> ids);

    // stop status
    public void stopStatus(String ids);

    // start status
    public void startStatus(String ids);

    // update
    public void updateWithDish(SetmealDto setmealDto);
}
