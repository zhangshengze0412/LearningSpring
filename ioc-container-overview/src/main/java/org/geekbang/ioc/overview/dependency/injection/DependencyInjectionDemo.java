package org.geekbang.ioc.overview.dependency.injection;

import org.geekbang.ioc.overview.annotation.Super;
import org.geekbang.ioc.overview.domain.User;
import org.geekbang.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * @className: DependencyInjectionDemo
 * @description: 依赖注入
 *                  1. 通过Bean名称注入
 *                  2. 根基Bean类型注入
 *                  3. 注入容器内建Bean对象
 *                  4. 注入非Bean对象
 *                  5. 注入类型
 * @author: ZSZ
 * @date: 2021/2/5 11:27
 */
public class DependencyInjectionDemo {

    public static void main(String[] args) {
        // 配置XML配置文件
        // 启动Spring应用上下文
//        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");

        // 第一来源：自定义Bean
        UserRepository userRepository = applicationContext.getBean("userRepository",UserRepository.class);

        // 依赖注入 得到的BeanFactory与此处的BeanFactory不是同一个
        /**
         * 疑问？
         *      为什么不相同？
         *          ApplicationContext与BeanFactory的关系？
         *          ApplicationContext 就是 BeanFactory
         *          ApplicationContext是BeanFactory的子接口，
         *          BeanFactory是IoC容器的基本实现，提供了基本的框架，
         *          而ApplicationContext是超集，为IoC容器提供更为完善和强大的功能
         *
         *          继承关系ClassPathXmlApplicationContext <- AbstractXmlApplicationContext
         *          <- AbstractRefreshableConfigXmlApplicationContext <- AbstractRefreshableApplicationContext
         *          <- AbstractApplicationContext <- ConfigurableApplicationContext
         *          <- ApplicationContext <- ListableBeanFactory <- BeanFactory
         *
         *          getBeanFactory()方法定义在 ConfigurableApplicationContext#getBeanFactory()
         *          具体实现在AbstractRefreshableApplicationContext中，
         *          在该类里组合了DefaultListableBeanFactory beanFactory，而不是直接继承或者实现，类似代理
         *
         *          两者都复用了BeanFactory接口，但是不是同一个对象
         *
         *      依赖注入得到的BeanFactory来源是哪里？ 容器内建依赖
         */
        // 第二来源： BeanFactory 容器内建依赖
        System.out.println(userRepository.getBeanFactory());
        System.out.println(userRepository.getBeanFactory() == applicationContext); // false

        ObjectFactory userFactory = userRepository.getObjectFactory();
        // 当注入的ObjectFactory<User> 与 ObjectFactory<ApplicationContext> 不同
        // 此处的ObjectFactory<ApplicationContext> 等于BeanFactory
        /**
         * 疑问？
         *      为什么ObjectFactory<ApplicationContext> 等于BeanFactory
         */
//        System.out.println(userFactory.getObject() == applicationContext);

        // 依赖查找
//        System.out.println(beanFactory.getBean(BeanFactory.class));

//        第三来源：容器内建的Bean
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("获取 Environment 类型的Bean"+environment);
    }




}
