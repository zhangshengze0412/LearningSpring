package org.geekbang.dependency.injection;

import org.geekbang.dependency.annotation.UserGroup;
import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Set;

/**
 * @className: LazyAnnotationDependencyInjectionDemo
 * @description: ObjectProvider 延迟依赖注入
 * @author: ZSZ
 * @date: 2021/2/11 16:12
 */
public class LazyAnnotationDependencyInjectionDemo {

    @Autowired
    private User user; // 实时注入

    @Autowired
    private ObjectProvider<User> userObjectProvider;    // 延迟注入

    @Autowired
    private ObjectFactory<Set<User>> userObjectFactory;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(LazyAnnotationDependencyInjectionDemo.class);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);

        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";

        reader.loadBeanDefinitions(xmlResourcePath);

        applicationContext.refresh();

        LazyAnnotationDependencyInjectionDemo demo = applicationContext.getBean(LazyAnnotationDependencyInjectionDemo.class);

        // 期待输出 superUser Bean
        System.out.println("demo.user = " + demo.user);
        // 期待输出 superUser Bean
        System.out.println("demo.userObjectProvider = " + demo.userObjectProvider.getObject());
        // 期待输出 superUser Bean
        System.out.println("demo.userObjectFactory = " + demo.userObjectFactory.getObject());
        demo.userObjectProvider.forEach(System.out::println);
        applicationContext.close();
    }

}
