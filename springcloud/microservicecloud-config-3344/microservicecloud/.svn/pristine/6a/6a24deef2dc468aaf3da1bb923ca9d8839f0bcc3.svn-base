package com.xh.springcloud.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xh.springcloud.entities.Dept;

import feign.hystrix.FallbackFactory;

@Component //不要忘记添加
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService>{

	@Override
	public DeptClientService create(Throwable arg0) {
		
		return new DeptClientService() {
			
			@Override
			public List<Dept> list() {
				
				return null;
			}
			
			@Override
			public Dept get(long id) {
				
			return new Dept().setDeptno(id).setDname("该ID:"+id+"没有对应的信息,cosumer客户端提供的降级信息")
						.setDb_source("no this database in Mysql");
			}
			
			@Override
			public boolean add(Dept dept) {
				
				return false;
			}
		};
	}

}
