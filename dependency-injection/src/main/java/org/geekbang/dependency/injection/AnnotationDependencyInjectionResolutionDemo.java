package org.geekbang.dependency.injection;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Lazy;

import java.util.Map;
import java.util.Optional;

/**
 * @className: AnnotationDependencyInjectionResolutionDemo
 * @description: 注解依赖注入 处理过程
 * @author: ZSZ
 * @date: 2021/2/11 16:12
 */
public class AnnotationDependencyInjectionResolutionDemo {

    @Autowired
    @Lazy                       // 依赖处理（查找）+延迟
    private User lazyUser;

    @Autowired          //                      依赖查找（处理）
    private User user; // DependencyDescriptor ->
                        //                      必须（required = true）
                        //                      实时注入（eager = true）
                        //                      通过类型User.class
                        //                      字段名称（"user"）
                        //                      是否是首要的（primary = true）

    @Autowired                          // 集合类型的依赖注入
    private Map<String,User> users;     // user superUser

    @Autowired
    private Optional<User> userOptional;    // superUser

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(AnnotationDependencyInjectionResolutionDemo.class);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);

        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";

        reader.loadBeanDefinitions(xmlResourcePath);

        applicationContext.refresh();

        AnnotationDependencyInjectionResolutionDemo demo = applicationContext.getBean(AnnotationDependencyInjectionResolutionDemo.class);

        // 期待输出 superUser Bean
        System.out.println("demo.user = " + demo.user);
        // 期待输出 superUser user
        System.out.println("demo.users = " + demo.users);
        // 期待输出 superUser Bean
        System.out.println("demo.userOptional = " + demo.userOptional);

        applicationContext.close();

    }

}
