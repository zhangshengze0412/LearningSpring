package org.geekbang.dependency.injection;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @className: XmlDependencyConstructorInjectionDemo
 * @description: 基于 API 的依赖Setter 方法注入示例
 * @author: ZSZ
 * @date: 2021/2/11 13:15
 */
public class APIDependencyConstructorInjectionDemo {

     public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 注册UserHolder 的 BeanDefinition
        BeanDefinition userHolderBeanDefinition = createUserHolderBeanDefinition();

        applicationContext.registerBeanDefinition("userHolder",userHolderBeanDefinition);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);

        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";

        reader.loadBeanDefinitions(xmlResourcePath);

        applicationContext.refresh();

        UserHolder userHolder = applicationContext.getBean(UserHolder.class);
        System.out.println(userHolder);

        applicationContext.close();
    }

//    @Bean
//    public UserHolder userHolder(User user){
//         UserHolder userHolder = new UserHolder();
//         userHolder.setUser(user);
//        return userHolder;
//    }

    /**
     * 为 UserHolder 生成BeanDefinition
     * @return
     */
    private static BeanDefinition createUserHolderBeanDefinition(){
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
        builder.addConstructorArgReference("superUser");
        return builder.getBeanDefinition();
    }

}
