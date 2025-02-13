package com.library.new_library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
	 @Bean
	 public LoginInterceptor SetInterceptor() {
	    return new LoginInterceptor();
	 }

	 @Override
	 public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(SetInterceptor())
	    		.addPathPatterns("/**"); // 拦截所有请求，通过判断是否有@UserLoginToken注解
	 }
}
