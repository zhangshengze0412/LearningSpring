package org.geekbang.bean.definition;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @className: BeanInstantiationDemo
 * @description: Bean 实例化实例
 * @author: ZSZ
 * @date: 2021/2/9 13:23
 */
public class BeanInstantiationDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation-context.xml");
        User user = (User) beanFactory.getBean("user-by-static-method");
        User userByInstance = (User) beanFactory.getBean("user-by-instance-method");
        User userByFactoryBean = (User) beanFactory.getBean("user-by-factory-bean");
        System.out.println("user-by-static-method" + user);
        System.out.println("user-by-instance-method" + userByInstance);
        System.out.println("user-by-factory-bean" + userByFactoryBean);
    }
}
