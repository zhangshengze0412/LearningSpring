package org.geekbang.ioc.bean.lifecycle;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

/**
 * @className: BeanMetadataConfigurationDemo
 * @description: Bean 元信息配置 示例
 * @author: ZSZ
 * @date: 2021/2/22 19:06
 */
public class BeanMetadataConfigurationDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 实例化 Properties 资源的 DefaultListableBeanFactory
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(beanFactory);
        String location = "META-INF/user.properties";
        // 加载资源
        // 指定字符编码 utf-8
        Resource resource = new ClassPathResource(location);
        EncodedResource encodedResource = new EncodedResource(resource,"UTF-8");
        int beanNumbers = reader.loadBeanDefinitions(encodedResource);
        System.out.println("已加载的BeanDefinition数量：" + beanNumbers);
        // ID 是配置文件前缀，通过Bean ID 和类型进行依赖查找
        User user = (User) beanFactory.getBean("user");
        System.out.println(user);
    }

}
