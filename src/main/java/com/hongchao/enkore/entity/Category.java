package com.hongchao.enkore.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Category
 */
@Data
public class Category implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Long id;

    //Type 1: dish category, 2: setmeal category
    private Integer type;

    //Category name
    private String name;

    //Sort order
    private Integer sort;

    //Creation time
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //Update time
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    //Creator
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    //Updater
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    //Is deleted
    private Integer isDeleted;

}
