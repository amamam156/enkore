package com.hongchao.enkore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongchao.enkore.entity.Category;

public interface CategoryService extends IService<Category>
{

    public void remove(Long id);
}
