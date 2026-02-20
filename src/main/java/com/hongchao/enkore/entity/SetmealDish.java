package com.hongchao.enkore.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Setmeal Dish Relationship
 */
@Data
public class SetmealDish implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Long id;

    //Setmeal ID
    private Long setmealId;

    //Dish ID
    private Long dishId;

    //Dish name (redundant field)
    private String name;

    //Original price of the dish
    private BigDecimal price;

    //Number of copies
    private Integer copies;

    //Sort order
    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    //Is deleted
    private Integer isDeleted;
}
