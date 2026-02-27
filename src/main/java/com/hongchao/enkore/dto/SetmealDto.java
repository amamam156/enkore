package com.hongchao.enkore.dto;

import com.hongchao.enkore.entity.Setmeal;
import com.hongchao.enkore.entity.SetmealDish;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data @EqualsAndHashCode(callSuper=false)
public class SetmealDto extends Setmeal
{

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
