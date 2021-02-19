package org.geekbang.dependency.injection;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @className: XmlDependencyConstructorInjectionDemo
 * @description: 基于 XML 资源的依赖 Constructor 方法注入示例
 * @author: ZSZ
 * @date: 2021/2/11 13:15
 */
public class XmlDependencyConstructorInjectionDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);

        String xmlResourcePath = "classpath:/META-INF/dependency-constructor-injection.xml";
        // 加载XML 资源 ，解析并生成BeanDefinition
        reader.loadBeanDefinitions(xmlResourcePath);
        // 创建依赖查找并创建Bean
        UserHolder userHolder = beanFactory.getBean(UserHolder.class);

        System.out.println(userHolder);
    }

}
