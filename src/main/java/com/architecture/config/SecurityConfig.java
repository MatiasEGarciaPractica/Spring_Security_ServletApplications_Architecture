package com.architecture.config;

import com.architecture.filter.FilterChainExceptionHandler;
import com.architecture.filter.TenantFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TenantFilter tenantFilter;
    private final FilterChainExceptionHandler filterChainExceptionHandler;

    @Autowired
    public SecurityConfig(TenantFilter tenantFilter, FilterChainExceptionHandler filterChainExceptionHandler){
        this.tenantFilter = tenantFilter;
        this.filterChainExceptionHandler = filterChainExceptionHandler;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(Customizer.withDefaults());
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        http.httpBasic(Customizer.withDefaults());
        http.formLogin(Customizer.withDefaults());

        //filter exception handler
        http.addFilterBefore(filterChainExceptionHandler, CsrfFilter.class);
        //to check tenant header
        http.addFilterBefore(tenantFilter, AuthorizationFilter.class);
        return http.build();
    }

    //to avoid duplicate invocation cause TenantFilter is a bean
    //I don't know why, but if I use this, my filter is not used.
    //@Bean
    //public FilterRegistrationBean<TenantFilter> tenantFilterRegistration(TenantFilter filter){
    //   FilterRegistrationBean<TenantFilter> registration = new FilterRegistrationBean<>(filter);
    //   registration.setEnabled(false);
    //   return registration;
    //}

}
