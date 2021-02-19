package org.geekbang.dependency.injection;

import org.geekbang.dependency.annotation.UserGroup;
import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

/**
 * @className: QualifierAnnotationDependencyInjectionDemo
 * @description: Qualifier 注解依赖注入
 * @author: ZSZ
 * @date: 2021/2/11 16:12
 */
public class QualifierAnnotationDependencyInjectionDemo {

    @Autowired
    private User user;       // superUser -> primary=true

    @Autowired
    @Qualifier("user")      // 指定Bean名称或ID
    private User namedUser;

    // 整体引用上下文存在4个User 类型的 Bean：
    // superUser
    // user
    // user1 -> @Qualifier
    // user2 -> @Qualifier

    @Autowired
    private Collection<User> allUsers;          // Beans = superUser + user

    @Autowired
    @Qualifier
    private Collection<User> qualifierUsers;        // Beans = user1 + user2 -> user1 user2 user3 user4

    @Autowired
    @UserGroup
    private Collection<User> groupedUsers;        // Beans = user3 + user4


    @Bean
    @Qualifier      // 进行逻辑分组
    public User user1(){
        return createUser(7l);
    }

    @Bean
    @Qualifier      // 进行逻辑分组
    public User user2(){
        return createUser(8l);
    }

    @Bean
    @UserGroup      // 进行逻辑分组
    public User user3(){
        return createUser(9l);
    }

    @Bean
    @UserGroup      // 进行逻辑分组
    public User user4(){
        return createUser(10l);
    }

    private static User createUser(Long id){
        User user =  new User();
        user.setId(id);
        return user;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(QualifierAnnotationDependencyInjectionDemo.class);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);

        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";

        reader.loadBeanDefinitions(xmlResourcePath);

        applicationContext.refresh();

        QualifierAnnotationDependencyInjectionDemo demo = applicationContext.getBean(QualifierAnnotationDependencyInjectionDemo.class);

        // 期待输出 superUser Bean
        System.out.println("demo.user = " + demo.user);
        // 期待输出 user Bean
        System.out.println("demo.namedUser = " + demo.namedUser);
        // 期待输出 superUser user
        System.out.println("demo.allUsers = " + demo.allUsers);
        // 期待输出 Beans = user1 + user2 -> user1 user2 user3 user4
        System.out.println("demo.qualifierUsers = " + demo.qualifierUsers);
        // 期待输出 user3 user4
        System.out.println("demo.groupedUsers = " + demo.groupedUsers);

        applicationContext.close();
    }

}
