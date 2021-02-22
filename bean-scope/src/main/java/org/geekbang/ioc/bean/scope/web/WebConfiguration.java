package org.geekbang.ioc.bean.scope.web;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @className: WebConfiguration
 * @description: Web MVC 配置
 * @author: ZSZ
 * @date: 2021/2/22 16:15
 */
@Configuration
@EnableWebMvc
public class WebConfiguration {

    @Bean
//    @RequestScope
//    @SessionScope
//    @ApplicationScope
    @Scope(WebApplicationContext.SCOPE_APPLICATION)
    public User user(){
        User user = new User();
        user.setId(1l);
        user.setName("zs");
        return user;
    }

}
