package org.geekbang.dependency.injection;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @className: XmlDependencyMethodInjectionDemo
 * @description: 基于 Java 注解的依赖 Method 方法注入示例
 * @author: ZSZ
 * @date: 2021/2/11 13:15
 */
public class AnnotationDependencyMethodInjectionDemo {

    private
//    static // autowired 会忽略静态字典
    UserHolder userHolder;

    private UserHolder userHolder2;

    @Autowired
    public void initUserHolder(UserHolder userHolder){
        this.userHolder = userHolder;
    }

    @Autowired
    public void initUserHolder2(UserHolder userHolder){
        this.userHolder2 = userHolder;
    }

    @Bean
    public UserHolder userHolder(User user){
        UserHolder userHolder = new UserHolder();
        userHolder.setUser(user);
        return userHolder;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 配置class 也是 Spring Bean
        applicationContext.register(AnnotationDependencyMethodInjectionDemo.class);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);

        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";

        reader.loadBeanDefinitions(xmlResourcePath);

        applicationContext.refresh();

        // 依赖查找 AnnotationDependencyFieldInjectionDemo
        AnnotationDependencyMethodInjectionDemo demo = applicationContext.getBean(AnnotationDependencyMethodInjectionDemo.class);
        UserHolder userHolder = demo.userHolder;
        System.out.println(userHolder);
        System.out.println(demo.userHolder2);
        System.out.println("userHolder == userHolder2 : " + (userHolder==demo.userHolder2));

        applicationContext.close();
    }

}
