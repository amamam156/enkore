package com.hongchao.enkore.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

// global exception handler
@ControllerAdvice(annotations = { RestController.class, Controller.class })
@ResponseBody
@Slf4j
public class GlobalExceptionHandler
{
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> excetionHandler(SQLIntegrityConstraintViolationException ex)
    {
        log.error(ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry"))
        {
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + " existed";
            return R.error(msg);
        }
        return R.error("Unknown errors");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> excetionHandler(CustomException ex)
    {
        log.error(ex.getMessage());

        return R.error(ex.getMessage());
    }
}
