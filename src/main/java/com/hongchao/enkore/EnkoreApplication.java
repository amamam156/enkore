package com.hongchao.enkore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@SpringBootApplication
@ServletComponentScan
public class EnkoreApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(EnkoreApplication.class,args);
        log.info("Program Start successful...");
    }
}