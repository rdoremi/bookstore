package com.book.store.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class ShopIntercetor implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptor = registry.addInterceptor(new ShopHandler());
        interceptor.addPathPatterns("/shop/page/**","/shop/product/**","/shop/user/**","/shop/order/**");
        interceptor.excludePathPatterns("/shop/page/login","/shop/user/login");

    }
}
