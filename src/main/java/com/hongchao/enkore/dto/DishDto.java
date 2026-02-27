package com.hongchao.enkore.dto;

import com.hongchao.enkore.entity.Dish;
import com.hongchao.enkore.entity.DishFlavor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data @EqualsAndHashCode(callSuper=false)
public class DishDto extends Dish
{

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
