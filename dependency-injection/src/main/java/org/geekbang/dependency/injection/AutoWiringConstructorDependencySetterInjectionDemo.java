package org.geekbang.dependency.injection;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @className: AutoWiringConstructorDependencySetterInjectionDemo
 * @description: "constructor" AutoWiring 依赖构造器注入 示例
 * @author: ZSZ
 * @date: 2021/2/11 14:08
 */
public class AutoWiringConstructorDependencySetterInjectionDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader  = new XmlBeanDefinitionReader(beanFactory);

        String xmlResourcePath = "classpath:/META-INF/autowiring-dependency-constructor-injection.xml";

        reader.loadBeanDefinitions(xmlResourcePath);

        UserHolder userHolder = beanFactory.getBean(UserHolder.class);

        System.out.println(userHolder);
    }

}
