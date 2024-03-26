package com.hongchao.enkore.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j

public class MymetaObjectHandler implements MetaObjectHandler
{
    @Override
    // update when insert
    public void insertFill(MetaObject metaObject)
    {
        log.info("Public AutoFill[insert]...");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

    @Override
    // update when update
    public void updateFill(MetaObject metaObject)
    {
        log.info("Public AutoFill[update]...");
        log.info(metaObject.toString());

        Long id = Thread.currentThread().getId();
        log.info("The thread ID is {}", id);

        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());

    }

}
