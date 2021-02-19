package org.geekbang.dependency.injection;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @className: XmlDependencySetterInjectionDemo
 * @description: 基于 Java 注解的依赖Setter 方法注入示例
 * @author: ZSZ
 * @date: 2021/2/11 13:15
 */
public class AnnotationDependencySetterInjectionDemo {

     public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(AnnotationDependencySetterInjectionDemo.class);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);

        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";

        reader.loadBeanDefinitions(xmlResourcePath);

        applicationContext.refresh();

        UserHolder userHolder = applicationContext.getBean(UserHolder.class);
        System.out.println(userHolder);

        applicationContext.close();
    }

    @Bean
    public UserHolder userHolder(User user){
         UserHolder userHolder = new UserHolder();
         userHolder.setUser(user);
        return userHolder;
    }

}
