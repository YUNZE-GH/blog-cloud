package com.gh.openInterface;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@MapperScan("com.gh.openInterface.modular.*.mapper")
@ComponentScans(value = {
        @ComponentScan(value = "com.gh.redis.*")
})
public class OpenInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenInterfaceApplication.class, args);
    }

}