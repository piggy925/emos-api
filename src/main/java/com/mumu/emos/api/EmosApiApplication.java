package com.mumu.emos.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@ServletComponentScan
@EnableAsync
@MapperScan("com.mumu.emos.api.db.dao")
@SpringBootApplication
public class EmosApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmosApiApplication.class, args);
    }

}