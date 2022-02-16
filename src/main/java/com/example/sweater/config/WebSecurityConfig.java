package com.example.sweater.config;

import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Override
    protected  void configure(HttpSecurity http)throws Exception{
        http
                .authorizeRequests()//включаем авторизацию
                    .antMatchers("/","registration").permitAll()//для указанного пути"/" включаем полный доступ
                    .anyRequest().authenticated()//для всех остальных требуем авторизацию
                .and()
                    .formLogin()//указываем форму
                    .loginPage("/login")//указываем мэппинг
                    .permitAll()//указываем для этого полный доступ
                .and()
                    .logout()//также указываем и разрешаем пользоваться всем
                    .permitAll();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
