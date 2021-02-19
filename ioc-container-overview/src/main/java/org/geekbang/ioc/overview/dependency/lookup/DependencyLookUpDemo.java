package org.geekbang.ioc.overview.dependency.lookup;

import org.geekbang.ioc.overview.annotation.Super;
import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @className: DependencyLookUpDemo
 * @description: 依赖查找
 *                  1. 通过Bean名称查找
 *                  2. 根基Bean类型查找
 *                  3. 根据Bean名称+类型查找
 *                  4. 根据
 * @author: ZSZ
 * @date: 2021/2/5 11:27
 */
public class DependencyLookUpDemo {

    public static void main(String[] args) {
        // 配置XML配置文件
        // 启动Spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");

        lookupByAnnotation(beanFactory);
        lookupByType(beanFactory);
        lookupByCollectionType(beanFactory);
//        lookupInRealTime(beanFactory);
//        lookupInLazy(beanFactory);
    }

    private static void lookupByAnnotation(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("根据注释（@Super）查找对象："+users.toString());
        }
    }

    //根据Bean类型查找集合对象
    private static void lookupByCollectionType(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String,User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("根据Bean类型查找集合对象："+users.toString());
        }

    }

    // 根据类型查找 泛型 Spring3.0实施
    private static void lookupByType(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println("根据Bean类型查找:" + user.toString());
    }

    //根据Bean名称查找：延迟查找
    // 所谓的延迟是指在注入时，不会马上注入目标对象，而是先弄一个句柄，当需要时，再次获取。
//    疑问？为什么采用ObjectFactory可以实现延迟查找
//    ObjectFactory 是按照需要查找 Bean
    private static void lookupInLazy(BeanFactory beanFactory) {
        //ObjectFactory 没有生成新的Bean，FactoryBean 生成新的Bean
//        ObjectFactoryCreatingFactoryBean 属于 FactoryBean 来创建 ObjectFactory，当依赖查找或依赖注入时，将返回 ObjectFactory 实例
//        ObjectFactoryCreatingFactoryBean 是 FactoryBean，这是一种特殊的 Bean 类型，它会返回其 getObjectType() 所指定的类型
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
//        在调用ObjectFactory的getObject()方法时，才创建User单例对象
        User user = objectFactory.getObject();
        System.out.println("根据Bean名称查找：延迟查找 - "+user.toString());
    }

    private static void lookupInRealTime(BeanFactory beanFactory) {
        //根据Bean名称查找：实时查找
        User user = (User) beanFactory.getBean("user");
        System.out.println("根据Bean名称查找：实时查找 - "+user.toString());
    }

}
