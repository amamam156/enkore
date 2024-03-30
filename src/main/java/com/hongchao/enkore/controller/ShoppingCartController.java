package com.hongchao.enkore.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hongchao.enkore.common.BaseContext;
import com.hongchao.enkore.common.R;
import com.hongchao.enkore.entity.ShoppingCart;
import com.hongchao.enkore.mapper.ShoppingCartMapper;
import com.hongchao.enkore.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController
{
    @Autowired
    private ShoppingCartService shoppingCartService;

    // add to shopping cart
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart)
    {

        log.info("Shopping cart: {}", shoppingCart);
        // set user id
        Long currencyId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currencyId);

        // check if same dish or meal
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currencyId);
        if (dishId != null)
        {
            // dish
            queryWrapper.eq(ShoppingCart::getDishId, dishId);

        } else
        {
            // meal
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        // check if exited
        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);
        if (cartServiceOne != null)
        {
            // exited
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            shoppingCartService.updateById(cartServiceOne);
        } else
        {
            // not exited
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }
        return R.success(cartServiceOne);
    }

    // shopping car

    @GetMapping("/list")
    public R<List<ShoppingCart>> list()
    {
        log.info("Preview shopping cart...");
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        return R.success(list);
    }

    // clean shopping cart
    @DeleteMapping("/clean")
    public R<String> clean()
    {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        shoppingCartService.remove(queryWrapper);

        return R.success("Clean Cart successful!");

    }

    @PostMapping("/sub")
    public R<ShoppingCart> remove(@RequestBody ShoppingCart shoppingCart)
    {

        log.info("Shopping cart: {}", shoppingCart);
        // set user id
        Long currencyId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currencyId);
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();

        // check
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currencyId);
        if (dishId != null)
        {
            // dish
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
            ShoppingCart dishCart = shoppingCartService.getOne(queryWrapper);
            Integer dishCartNumber = dishCart.getNumber();
            if (dishCartNumber == 1)
            {
                dishCart.setNumber(0);
                shoppingCartService.removeById(dishCart.getId());

            } else if (dishCartNumber > 1)
            {
                dishCart.setNumber(dishCartNumber - 1);
                shoppingCartService.updateById(dishCart);
            }
            return R.success(dishCart);
        }
        if (setmealId != null)
        {
            // meal
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());

            // check if exited
            ShoppingCart mealCart = shoppingCartService.getOne(queryWrapper);
            Integer mealCartNumber = mealCart.getNumber();
            if (mealCartNumber > 1)
            {
                mealCart.setNumber(mealCartNumber - 1);
                shoppingCartService.updateById(mealCart);
            } else if (mealCartNumber == 1)
            {
                mealCart.setNumber(0);
                shoppingCartService.removeById(mealCart.getId());
            }
            return R.success(mealCart);

        }
        return R.error("Unknown error");

    }
}