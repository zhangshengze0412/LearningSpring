package org.geekbang.ioc.overview.container;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @className: AnnotationApplicationContextAsIoCContainerDemo
 * @description: ApplicationContext 作为 IoC容器实例
 * @author: ZSZ
 * @date: 2021/2/8 15:10
 */
//@Configuration
public class AnnotationApplicationContextAsIoCContainerDemo {

    public static void main(String[] args) {
        // 创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 AnnotationApplicationContextAsIoCContainerDemo 作为配置配 （Configuration Class）
        applicationContext.register(AnnotationApplicationContextAsIoCContainerDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
//         依赖查找集合对象
        lookupByCollectionType(applicationContext);

        applicationContext.close();
    }

    /**
     * 通过Java注解的方式，定义了一个类
     * @return
     */
    @Bean
    public User user(){
        User user = new User();
        user.setId(1l);
        user.setName("zs");
        return user;
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
