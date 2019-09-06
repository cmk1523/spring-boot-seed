package com.techdevsolutions.messenger;

import com.techdevsolutions.messenger.controllers.ControllerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SpringMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Need thymeleaf for this to work
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ControllerInterceptor())
                .addPathPatterns("/api/**");
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.techdevsolutions"))
                .paths(PathSelectors.ant("/api/v1/**"))
                .build()
                .pathMapping("/")
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "My REST API",
                "My REST API description",
                "0.0.1",
                "My terms of service",
                new Contact("John Doe", "www.example.com", "myeaddress@company.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}