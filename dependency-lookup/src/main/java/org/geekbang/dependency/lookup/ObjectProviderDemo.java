package org.geekbang.dependency.lookup;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Iterator;

/**
 * @className: ObjectProviderDemo
 * @description: 通过ObjectProvider 进行依赖查找
 * @author: ZSZ
 * @date: 2021/2/10 16:41
 */
public class ObjectProviderDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ObjectProviderDemo.class);
        applicationContext.refresh();

        lookupByObjectProvider(applicationContext);
        lookupIfAvailable(applicationContext);
        lookupByStreamOps(applicationContext);

        applicationContext.close();
    }

    private static void lookupByStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
//        Iterable<String> stringIterable= objectProvider;
//        for (String string:stringIterable) {
//            System.out.println(string);
//        }
        // stream()
        objectProvider.stream().forEach(System.out::println);

    }

    private static void lookupIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        User user = userObjectProvider.getIfAvailable(User::createUser);
        System.out.println("当前 User 对象：" + user);
    }

    @Bean
    @Primary
    public String helloWorld(){         //@Bean里没有定义value或者name，方法名就是Bean的名称 = ’helloWorld‘
        return "Hello,World!";
    }

    @Bean
    public String message(){
        return "Message";
    }

    private static void lookupByObjectProvider(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        System.out.println(objectProvider.getObject());
    }

}
