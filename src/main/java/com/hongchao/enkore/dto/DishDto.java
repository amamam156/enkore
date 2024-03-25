package com.hongchao.enkore.dto;


import com.hongchao.enkore.entity.Dish;
import com.hongchao.enkore.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
