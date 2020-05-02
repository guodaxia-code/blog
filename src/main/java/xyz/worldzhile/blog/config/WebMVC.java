package xyz.worldzhile.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.worldzhile.blog.interceptor.LoginInterceptor;

@Configuration
public class WebMVC implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/admin/**").excludePathPatterns("/admin/login");
    }

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

}
