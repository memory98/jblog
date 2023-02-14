package com.douzone.jblog.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.douzone.jblog.interceptor.AdminInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	
	// Arugument Resolver

	
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