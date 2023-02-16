package com.douzone.jblog.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.douzone.jblog.interceptor.AdminInterceptor;

@SpringBootConfiguration
public class WebConfig implements WebMvcConfigurer {
	// Interceptors
	@Bean
	public HandlerInterceptor adminInterceptor() {
		return new AdminInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(adminInterceptor())
			.addPathPatterns("/*/admin/**");
	}
}