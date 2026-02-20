package com.hongchao.enkore.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Address Book
 */
@Data
public class AddressBook implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Long id;

    //User ID
    private Long userId;

    //Consignee
    private String consignee;

    //Phone number
    private String phone;

    //Gender 0: female, 1: male
    private String sex;

    //Province code
    private String provinceCode;

    //Province name
    private String provinceName;

    //City code
    private String cityCode;

    //City name
    private String cityName;

    //District code
    private String districtCode;

    //District name
    private String districtName;

    //Detailed address
    private String detail;

    //Label
    private String label;

    //Is default 0: no, 1: yes
    private Integer isDefault;

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
