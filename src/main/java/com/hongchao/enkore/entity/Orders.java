package com.hongchao.enkore.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// order
@Data
public class Orders implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Long id;

    //order number
    private String number;

    //Order status 1 pending payment, 2 awaiting delivery, 3 delivered, 4 completed, 5 Cancel
    private Integer status;

    //Order user id
    private Long userId;

    //address id
    private Long addressBookId;

    //order time
    private LocalDateTime orderTime;

    //Checkout time
    private LocalDateTime checkoutTime;

    //Payment method 1 WeChat, 2 Alipay
    private Integer payMethod;

    //Amount actually received
    private BigDecimal amount;

    //Remark
    private String remark;

    //username
    private String userName;

    //Phone number
    private String phone;

    //address
    private String address;

    //Receiver
    private String consignee;
}
