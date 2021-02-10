package org.geekbang.bean.definition;

import org.geekbang.bean.factory.DefaultUserFactory;
import org.geekbang.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @className: BeanInitializationDemo
 * @description: Bean 初始化 实例
 * @author: ZSZ
 * @date: 2021/2/9 15:11
 */

public class BeanInitializationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanInitializationDemo.class);
        applicationContext.refresh();
        System.out.println("Spring 应用上下文已启动。。。。");
        // 延迟初始化，是一种按需初始化的方式，
        // 只用当依赖查找或者依赖注入按需要触发时才进行初始化Bean
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);
        System.out.println(userFactory);
        System.out.println("Spring 应用上下文准备关闭。。。。");
        applicationContext.close();
        System.out.println("Spring 应用上下文已关闭。。。。");
    }

    @Bean(initMethod = "initUserFactory",destroyMethod = "doDestroy")
    @Lazy(false)
    public UserFactory userFactory(){
        return new DefaultUserFactory();
    }

}
