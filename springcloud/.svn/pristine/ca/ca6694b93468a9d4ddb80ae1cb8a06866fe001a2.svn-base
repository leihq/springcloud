package com.xh.ribbon.myrule;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;

@Configuration
public class MySelfRule {
	@Bean
	public IRule MyRule(){
		 return new RoundRobinRule_zy();
	}
}