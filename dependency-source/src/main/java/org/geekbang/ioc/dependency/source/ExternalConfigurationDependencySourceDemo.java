package org.geekbang.ioc.dependency.source;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

/**
 * @className: ExternalConfigurationDependencySourceDemo
 * @description: 外部化配置作为依赖来源
 * @author: ZSZ
 * @date: 2021/2/21 16:52
 */
@Configuration
// 为什么需要加上Configuration？
@PropertySource(value = "META-INF/default.properties",encoding = "utf-8")
public class ExternalConfigurationDependencySourceDemo {

    @Value("${user.id:-1}")
    private Long id;

    @Value("${user.name}")
    private String name;            // 由于优先级问题输出DELL

    @Value("${user.name2}")
    private String name2;           // 中文乱码,解决：在外部化配置@PropertySource（）中增加encoding

    @Value("${user.resource:classpath://default.properties}")
    private Resource resource;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(ExternalConfigurationDependencySourceDemo.class);

        applicationContext.refresh();

        ExternalConfigurationDependencySourceDemo demo = applicationContext.getBean(ExternalConfigurationDependencySourceDemo.class);

        System.out.println("demo.id = " + demo.id);
        System.out.println("demo.name = " + demo.name);
        System.out.println("demo.name2 = " + demo.name2);
        System.out.println("demo.resource = " + demo.resource);

        applicationContext.close();
    }

}
