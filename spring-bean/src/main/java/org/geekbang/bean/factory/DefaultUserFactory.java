package org.geekbang.bean.factory;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @className: DefaultUserFactory
 * @description: 默认实现
 * @author: ZSZ
 * @date: 2021/2/9 13:30
 */
public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {

    // 1.基于PostConstruct注解
    @PostConstruct
    public void init(){
        System.out.println("@PostConstruct：UserFactory 初始化中。。。。");
    }

    // 2. 实现InitializingBean接口的afterPropertiesSet（）方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet()：UserFactory 初始化中。。。。");

    }
    // 3.Java注解：@Bean(initMethod=“init”)
    public void initUserFactory(){
        System.out.println("自定义初始化方法 initUserFactory：UserFactory 初始化中。。。。");

    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("@PreDestroy: UserFactory 销毁中 。。。。");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destroy()：UserFactory 销毁中。。。。");
    }

    public void doDestroy(){
        System.out.println("自定义销毁方法 doDestroy：UserFactory 销毁中。。。。");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("当前 DefaultUserFactory 对象正在被垃圾回收 。。。。 ");
    }
}
