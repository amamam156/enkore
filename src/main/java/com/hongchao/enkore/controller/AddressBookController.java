package com.hongchao.enkore.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import com.hongchao.enkore.common.BaseContext;
import com.hongchao.enkore.common.CustomException;
import com.hongchao.enkore.common.R;
import com.hongchao.enkore.entity.AddressBook;
import com.hongchao.enkore.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// address book
@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController
{

    @Autowired
    private AddressBookService addressBookService;

    // add
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook)
    {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }

    // set as default
    @PutMapping("default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook)
    {
        log.info("addressBook:{}", addressBook);
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        wrapper.set(AddressBook::getIsDefault, 0);
        //SQL:update address_book set is_default = 0 where user_id = ?
        addressBookService.update(wrapper);

        addressBook.setIsDefault(1);
        //SQL:update address_book set is_default = 1 where id = ?
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }

    // get id
    @GetMapping("/{id}")
    public R get(@PathVariable Long id)
    {
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null)
        {
            return R.success(addressBook);
        } else
        {
            return R.error("No object");
        }
    }

    // get default
    @GetMapping("default")
    public R<AddressBook> getDefault()
    {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        queryWrapper.eq(AddressBook::getIsDefault, 1);

        //SQL:select * from address_book where user_id = ? and is_default = 1
        AddressBook addressBook = addressBookService.getOne(queryWrapper);

        if (null == addressBook)
        {
            return R.error("No object");
        } else
        {
            return R.success(addressBook);
        }
    }

    // address list
    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook)
    {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);

        // create filter
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != addressBook.getUserId(), AddressBook::getUserId, addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);

        //SQL:select * from address_book where user_id = ? order by update_time desc
        return R.success(addressBookService.list(queryWrapper));
    }

    @PutMapping
    public R<String> updateAdd(@RequestBody AddressBook addressBook)
    {
        if (addressBook == null)
        {
            throw new CustomException("The address information does not exist, please refresh and try again");
        }
        addressBookService.updateById(addressBook);
        return R.success("Edit address successful");
    }
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids)
    {
        log.info("delete address, id is: {}", ids);
        addressBookService.removeByIds(ids);
        return R.success("Delete address successful!");
    }
}

