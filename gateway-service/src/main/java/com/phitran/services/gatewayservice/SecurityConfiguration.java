package com.phitran.services.gatewayservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests()
//                .antMatchers("/", "/securedPage", "/swagger-ui.html**").permitAll()
                .anyRequest().permitAll()
                .and().oauth2Login().defaultSuccessUrl("/securedPage")
                .and().oauth2ResourceServer().jwt();
    }

}
