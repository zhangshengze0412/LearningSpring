package org.geekbang.dependency.lookup;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @className: TypeSafetyDependencyLookupDemo
 * @description: 类型安全 依赖查找示例
 * @author: ZSZ
 * @date: 2021/2/10 19:14
 */
public class TypeSafetyDependencyLookupDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TypeSafetyDependencyLookupDemo.class);
        applicationContext.refresh();

        // 演示 BeanFactory#getBean的安全性
        displayBeanFactoryGetBean(applicationContext);
        // 演示 ObjectFactory#getObject的安全性
        displayObjectFactoryGetObject(applicationContext);
        // 演示 ObjectProvider#getIfAvailable的安全性
        displayObjectProviderGetIfAvailable(applicationContext);
        // 演示 ListableBeanFactory#getBeansOfType的安全性
        displayListableBeanFactoryGetBeansOfType(applicationContext);
        // 演示 ObjectProvider的Stream操作的安全性
        displayObjectProviderStreamOps(applicationContext);
        applicationContext.close();
    }

    private static void displayObjectProviderStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectProviderStreamOps",()->userObjectProvider.forEach(System.out::println));
    }

    private static void displayListableBeanFactoryGetBeansOfType(ListableBeanFactory beanFactory) {
        printBeansException("displayListableBeanFactoryGetBeansOfType",()->beanFactory.getBeansOfType(User.class));
    }

    private static void displayObjectProviderGetIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectProviderGetIfAvailable",()->userObjectProvider.getIfAvailable());
    }

    private static void displayObjectFactoryGetObject(AnnotationConfigApplicationContext applicationContext) {
        // ObjectProvider is ObjectFactory
        ObjectFactory<User> userObjectFactory = applicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectFactoryGetObject",() -> userObjectFactory.getObject());
    }

    public static void displayBeanFactoryGetBean(BeanFactory beanFactory){
        printBeansException("displayBeanFactoryGetBean",() -> beanFactory.getBean(User.class));
    }

    private static void printBeansException(String source, Runnable runnable){
        System.out.println("===========================================");
        System.out.println("Source from:"+source);
        try{
            runnable.run();
        }catch (BeansException exception){
            exception.printStackTrace();
        }
    }

}
