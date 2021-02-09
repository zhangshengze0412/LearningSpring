package org.geekbang.ioc.overview.container;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * @className: BeanFactoryIoCContainerDemo
 * @description: BeanFactory 作为 IoC容器实例
 *                  流程：
 *                      1. 创建BeanFactory容器（基本的BeanFactory 如DefaultListableBeanFactory）
 *                      2. 创建XmlBeanDefinitionReader
 *                      3. 加载XML配置文件
 *                      4. 依赖查找
 * @author: ZSZ
 * @date: 2021/2/8 15:10
 */
public class BeanFactoryIoCContainerDemo {

    public static void main(String[] args) {
        // 创建BeanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // XML 配置文件 ClassPath路径
        String location = "classpath:/META-INF/dependency-lookup-context.xml";
        // 加载配置
        int beanDefinitionsCount = reader.loadBeanDefinitions(location);
        System.out.println("Bean定义加载数量:" + beanDefinitionsCount);

        // 依赖查找集合对象
        lookupByCollectionType(beanFactory);
    }

    //根据Bean类型查找集合对象
    private static void lookupByCollectionType(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("根据Bean类型查找集合对象："+users.toString());
        }

    }

}
