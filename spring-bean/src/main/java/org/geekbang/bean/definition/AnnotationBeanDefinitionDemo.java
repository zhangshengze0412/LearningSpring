package org.geekbang.bean.definition;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @className: AnnotationBeanDefinitionDemo
 * @description: 注解 BeanDefinition 示例
 * @author: ZSZ
 * @date: 2021/2/9 9:57
 */
@Import(AnnotationBeanDefinitionDemo.Config.class)// 3. 通过@Import来进行导入
public class AnnotationBeanDefinitionDemo {

    public static void main(String[] args) {
        // 创建BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 注册Configuration Class（配置类）
        applicationContext.register(AnnotationBeanDefinitionDemo.class);

//        applicationContext.refresh();

        // 通过Java API方式注册BeanDefinition
        // 命名 Bean 的注册方式
        registerBeanDefinition(applicationContext,"ls");
        // 非命名 Bean 的注册方式
        registerBeanDefinition(applicationContext);

        // 启动容器
        applicationContext.refresh();
        // 1. 通过@Bean方式定义
        // 2. 通过@Component方式
        // 3. 通过@Import来进行导入
        // 显示关闭Spring应用上下文

        System.out.println("Config 类型所有的Beans" + applicationContext.getBeansOfType(Config.class));
        System.out.println("User 类型所有的Beans" + applicationContext.getBeansOfType(User.class));
        applicationContext.close();
    }

    /**
     * 命名 Bean 的注册方式
     * @param registry
     * @param beanName
     */
    public static void registerBeanDefinition(BeanDefinitionRegistry registry,String beanName){
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        builder.addPropertyValue("id",1);
        builder.addPropertyValue("name","zs");
        if(StringUtils.hasText(beanName)){
            // 命名 bean 的注册方式
            registry.registerBeanDefinition(beanName,builder.getBeanDefinition());
        }else{
            // 非命名 Bean 的注册方式
            BeanDefinitionReaderUtils.registerWithGeneratedName(builder.getBeanDefinition(),registry);
        }

    }

    /**
     * 非命名 Bean 的注册方式
     * @param registry
     */
    public static void registerBeanDefinition(BeanDefinitionRegistry registry){
        registerBeanDefinition(registry,null);
    }

    // 2. 通过@Component方式
    @Component // 定义当前类作为SpringBean
    public static class Config{
        // 1. 通过@Bean方式定义
        @Bean({"user","zs-user"})
        public User user(){
            User user = new User();
            user.setId(1l);
            user.setName("zs");
            return user;
        }
    }
}
