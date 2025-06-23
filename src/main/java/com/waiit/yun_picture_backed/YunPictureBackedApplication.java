package com.waiit.yun_picture_backed;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.waiit.yun_picture_backed.mapper") // 扫描mapper
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class YunPictureBackedApplication {

    public static void main(String[] args) {
        SpringApplication.run(YunPictureBackedApplication.class, args);
    }

}
  