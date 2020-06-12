package com.book.store;


import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement(proxyTargetClass=true)
@SpringBootApplication
@MapperScan("com.book.store.mapper")
public class StoreApplication{

	/*@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration interceptor = registry.addInterceptor(new MyIntercetor());
		interceptor.addPathPatterns("/admin/page/**");
		interceptor.excludePathPatterns("/admin/page/login");

	}*/

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}

}
