package com.book.store.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class AdminIntercetor implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptor = registry.addInterceptor(new MyIntercetor());
        interceptor.addPathPatterns("/admin/page/**","/admin/order/**","/manage/categroy/**","/admin/user/**","/manage/product/**","/manage/shop/**","/admin/account/**");
        interceptor.excludePathPatterns("/admin/page/login","/admin/user/login");
    }
}
