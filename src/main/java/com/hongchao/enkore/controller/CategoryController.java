package com.hongchao.enkore.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongchao.enkore.common.R;
import com.hongchao.enkore.entity.Category;
import com.hongchao.enkore.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Page management
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController
{
    @Autowired
    private CategoryService categoryService;

    // add new category
    @PostMapping
    public R<String> save(@RequestBody Category category)
    {
        log.info("category: {}", category);
        categoryService.save(category);
        return R.success("Add category successful!");
    }

    // Paging query

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize)
    {
        // paging creator
        Page<Category> pageInfo = new Page<>(page, pageSize);
        // conditional constructor
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        // Add sorting criteria
        queryWrapper.orderByAsc(Category::getSort);
        // do
        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);

    }

    // delete by id
    @DeleteMapping
    public R<String> delete(Long id)
    {
        log.info("delete category, id is: {}", id);
        // categoryService.removeById(id);
        categoryService.remove(id);
        return R.success("Delete category successful!");
    }

    // modify category by id
    @PutMapping
    public R<String> update(@RequestBody Category category)
    {
        log.info("Modify category: {}", category);
        categoryService.updateById(category);
        return R.success("Modify category successful!");
    }

    // search by condition
    @GetMapping("/list")
    public R<List<Category>> list(Category category)
    {

        // conditional constructor

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        // add condition

        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());

        // add sort condition

        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
