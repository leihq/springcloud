package com.xh.springcloud.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.xh.springcloud.entities.Dept;

@RestController
public class DeptController_Consumer {
//	private static final String REST_URL_PREFIX ="http://localhost:8001";//部门微服务提供者地址前缀
	private static final String REST_URL_PREFIX ="http://MICROSERVICECLOUD-DEPT";//部门微服务提供者地址前缀
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value="/consumer/dept/add")
	public boolean add(Dept dept){
		//三个参数：url-REST请求地址，request-请求参数，ResponBean.class-http响应转换成的对象类型
		return restTemplate.postForObject(REST_URL_PREFIX+"/dept/add", dept, boolean.class);
	}
	
	
	@RequestMapping(value="/consumer/dept/list")
	public List<Dept> list(){
		return restTemplate.getForObject(REST_URL_PREFIX+"/dept/list",List.class);
	}
	
	@RequestMapping(value="/consumer/dept/get/{id}")
	public Dept get(@PathVariable("id") Long id){
		return restTemplate.getForObject(REST_URL_PREFIX+"/dept/get/"+id, Dept.class);
	}
	
	//测试@EnableDiscoveryClient,消费端可以调用服务发现
	@RequestMapping(value="/consumer/dept/discovery")
	public Object discovery(){
		return restTemplate.getForObject(REST_URL_PREFIX+"/dept/discovery", Object.class);
	}
	
}
