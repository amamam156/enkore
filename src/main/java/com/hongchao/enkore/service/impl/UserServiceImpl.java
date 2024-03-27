package com.hongchao.enkore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongchao.enkore.entity.User;
import com.hongchao.enkore.mapper.UserMapper;
import com.hongchao.enkore.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService
{
}
