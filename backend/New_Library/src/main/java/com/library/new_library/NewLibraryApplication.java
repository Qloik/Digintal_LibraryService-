package com.library.new_library;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync // 开启异步任务
@EnableScheduling //定时器注解
@MapperScan(value = "com.library.new_library.mapper")
public class NewLibraryApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(NewLibraryApplication.class, args);
    }

}
