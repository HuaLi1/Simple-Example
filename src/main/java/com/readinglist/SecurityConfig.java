package com.readinglist;

import com.readinglist.api.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * SecurityConfig是个非常基础的Spring Security配置，尽管如此，它还是完成了不少安全定
 * 制工作。通过这个自定义的安全配置类，我们让Spring Boot跳过了安全自动配置，转而使用我们
 * 的安全配置。
 * 扩展了WebSecurityConfigurerAdapter的配置类可以覆盖两个不同的configure()方
 * 法。在SecurityConfig里，第一个configure()方法指明，“/”（ReadingListController
 * 的方法映射到了该路径）的请求只有经过身份认证且拥有READER角色的用户才能访问。其他的
 * 所有请求路径向所有用户开放了访问权限。这里还将登录页和登录失败页（带有一个error属性）
 * 指定到了/login。
 * Spring Security为身份认证提供了众多选项，后端可以是JDBC（Java Database Connectivity）、
 * LDAP和内存用户存储。在这个应用程序中，我们会通过JPA用数据库来存储用户信息。第二个
 * configure()方法设置了一个自定义的UserDetailsService，这个服务可以是任意实现了
 * UserDetailsService的类，用于查找指定用户名的用户。简单地调用了注入ReaderRepository（这是一个Spring Data JPA仓库接口）的findById
 * 方法。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ReaderRepository readerRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/Reading").access("hasRole('READER')")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
             @Override
             public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
                    return readerRepository.findById(username).orElse(null);
             }
        });
    }
}
