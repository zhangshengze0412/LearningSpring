package org.geekbang.bean.definition;

import org.geekbang.bean.factory.DefaultUserFactory;
import org.geekbang.bean.factory.UserFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @className: SingletonBeanRegistrationDemo
 * @description: 外部单体
 * @author: ZSZ
 * @date: 2021/2/9 21:55
 */
public class SingletonBeanRegistrationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //创建外部对象
        UserFactory userFactory = new DefaultUserFactory();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        // 注册外部单例对象
        beanFactory.registerSingleton("userFactory",userFactory);
        applicationContext.refresh();

        UserFactory userFactoryLookup = beanFactory.getBean("userFactory",UserFactory.class);
        System.out.println("userFactory == userFactoryLookup : " + (userFactory == userFactoryLookup));
        applicationContext.close();
    }

}
