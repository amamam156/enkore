package com.hongchao.enkore.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Dish
 */
@Data
public class Dish implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Long id;

    //Dish name
    private String name;

    //Dish category ID
    private Long categoryId;

    //Dish price
    private BigDecimal price;

    //Product code
    private String code;

    //Image
    private String image;

    //Description
    private String description;

    //0: stopped selling, 1: selling
    private Integer status;

    //Order
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
