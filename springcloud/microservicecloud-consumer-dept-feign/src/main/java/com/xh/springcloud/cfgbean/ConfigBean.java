package com.xh.springcloud.cfgbean;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;

@Configuration
public class ConfigBean { //boot -->spring applicationContext.xml -- Configuration配置
	@Bean
	@LoadBalanced
	public  RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	
	/**
	 * 负载均衡策略设置,如果不写此方法，默认策略是轮询策略 RoundRobinRule 
	 * @return
	 */
	@Bean
	public IRule MyRule(){
		return new RandomRule(); //随机规则，可以换其他规则   如 new AvailbilityFilteringRule();
	}
}
