package com.hongchao.enkore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongchao.enkore.entity.AddressBook;
import com.hongchao.enkore.mapper.AddressBookMapper;
import com.hongchao.enkore.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService
{
}
