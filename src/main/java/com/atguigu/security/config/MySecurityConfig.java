package com.atguigu.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @EnableWebSecurity注解中已经包含了@Configuration注解，所以不需要导入
 *
 */
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 定制请求规则
        http.authorizeRequests().antMatchers("/").permitAll()
                // 定义权限是vip1的用户才可以访问/level1/**下的内容
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        // 开启自动配置的登录功能，效果是如果没有权限，来到默认页面Security自动创建的页面
        // http.formLogin();
        // 定制登录页面，用我们自己写的
        // 默认post形式的/login代表处理登录
        // 一旦定制loginPage，那么loginPage的post请求就是登陆
        http.formLogin().usernameParameter("user").passwordParameter("pwd").loginPage("/userlogin");

        // 1.默认开启/login来到登录页面
        // 2.重定向到/login?error表示登陆失败

        // 开启自动配置的注销功能，定制返回页面
        http.logout().logoutSuccessUrl("/");// 注销成功以后来到首页
        // 1.访问/logout 表示用户注销  清空Session
        // 2.默认来到/login?logout页面


        // 开启记住我功能,登录成功以后将Cookie发送给浏览器，以后登录带上Cookie
        // 点击注销就可以删除cookie
        // http.rememberMe();

        // 开启记住我功能 定制登录页面的记住我
        http.rememberMe().rememberMeParameter("remember");

    }

    // 定义认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // super.configure(auth);
        auth.inMemoryAuthentication()
                .withUser("zhangsan").password("123456").roles("VIP1","VIP2")
                .and()
                .withUser("lisi").password("123456").roles("VIP2","VIP3")
                .and()
                .withUser("wangwu").password("123456").roles("VIP1","VIP3");

    }
}
