package com.gh.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;


//@EnableDiscoveryClient eureka开启发现服务功能
@EnableFeignClients(basePackages = "com.gh.consumer.feign")
//@ComponentScan(basePackages = "com.gh.consumer.*")
@ComponentScans(value = {
		@ComponentScan(value = "com.gh.consumer.*")
		,@ComponentScan(value = "com.gh.redis.*")
})
@EnableScheduling	// 开启定时任务功能
@SpringBootApplication
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
}
