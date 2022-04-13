package com.shivan.oauthdemo.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        //http.authorizeRequests().antMatchers("/").hasRole("USER").antMatchers("/login/*").permitAll().and().httpBasic();
        http.antMatcher("/").authorizeRequests()
                .antMatchers("/ums/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login();
    }



}
