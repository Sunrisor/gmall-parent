package com.atguigu.gmall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {


    /**
     * 配置跨域资源共享
     * @return
     */
    @Bean
    public CorsWebFilter corsWebFilter(){

        //创建对象
        CorsConfiguration configuration=new CorsConfiguration();
        //允许跨域的网路地址
        configuration.addAllowedOrigin("*");
        //允许cookie携带
        configuration.setAllowCredentials(true);
        //允许的请求方式
        configuration.addAllowedMethod("*");
        //允许携带头信息
        configuration.addAllowedHeader("*");

        //创建配置对象
        UrlBasedCorsConfigurationSource configurationSource=new UrlBasedCorsConfigurationSource();
        //设置配置
        configurationSource.registerCorsConfiguration("/**",configuration);
        return new CorsWebFilter(configurationSource);
    }


}
