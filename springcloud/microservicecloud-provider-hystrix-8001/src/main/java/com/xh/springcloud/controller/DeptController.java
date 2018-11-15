package com.xh.springcloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xh.springcloud.entities.Dept;
import com.xh.springcloud.service.DeptService;

@RestController
public class DeptController {
	
	@Autowired
	private DeptService deptService;
	

	
	
	
	@RequestMapping(value="/dept/get/{id}",method=RequestMethod.GET)
	//当调用此方法失败并错误信息后，会自动调用@HystrixCommand 中标注好的fallbackMethod指定的方法
	@HystrixCommand(fallbackMethod="processHystrix_Get")
	public Dept get(@PathVariable("id") Long id){
		Dept dept = deptService.get(id);
		if(null == dept){
			throw new RuntimeException("该id:"+id+"没有对应的信息");
		}
		return deptService.get(id);
	}
	
	public Dept processHystrix_Get(@PathVariable("id") Long id){
		return new Dept().setDeptno(id).setDname("该ID:"+id+"没有对应的信息,null -->@HystricCommand")
				.setDb_source("no this database in Mysql");
	}
	
	
}
 