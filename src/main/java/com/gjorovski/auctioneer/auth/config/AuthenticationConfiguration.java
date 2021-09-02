package com.gjorovski.auctioneer.auth.config;

import com.gjorovski.auctioneer.auth.filter.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class AuthenticationConfiguration {
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilterRegistrationBean() {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();

        registrationBean.setFilter(authenticationFilter);
        registrationBean.setUrlPatterns(Collections.singleton("/api/*"));
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
