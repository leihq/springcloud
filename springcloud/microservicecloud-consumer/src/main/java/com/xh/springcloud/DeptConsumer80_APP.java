package com.xh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.xh.ribbon.myrule.MySelfRule;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name="MICROSERVICECLOUD-DEPT",configuration=MySelfRule.class)//配置自定义负载均衡策略
public class DeptConsumer80_APP {

	public static void main(String[] args) {
		SpringApplication.run(DeptConsumer80_APP.class, args);
	}

}
