package com.gh.baseUserSystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@MapperScan("com.gh.*.modular.*.mapper")	// 需要兼顾扫描到jar包里的mapper
//@ComponentScans(value = {
//		@ComponentScan(value = "com.gh.redis.*"),
//		@ComponentScan(value = "com.gh.auth.*")
//})
@ComponentScan(value = "com.gh.*")
public class BaseUserSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseUserSystemApplication.class, args);
	}

}
