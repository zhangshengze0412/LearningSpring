package org.geekbang.ioc.bean.scope;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Map;

/**
 * @className: BeanScopeDemo
 * @description: Bean的作用域
 * @author: ZSZ
 * @date: 2021/2/21 22:26
 */
public class BeanScopeDemo implements DisposableBean {

    @Bean
    // 默认Singleton
    public static User singletonUser(){
        return createUser();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    @Scope("prototype")
    public static User prototypeUser(){
        return createUser();
    }

    public static User createUser(){
        User user = new User();
        user.setId(System.nanoTime());
        return user;
    }

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser;

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser1;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser1;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser2;

    @Autowired
    private Map<String,User> map;

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;            // Resolvable Dependency

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(BeanScopeDemo.class);

        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
                @Override
                public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                    System.out.printf("%s Bean 名称：%s 在初始化后回调...%n",bean.getClass().getName(),beanName);
                    return bean;
                }
            });
        });

        applicationContext.refresh();

        /**
         * 结论一：
         *      Singleton Bean 无论依赖查找还是依赖注入，均为同一个对象
         *      Prototype Bean 无论依赖查找还是依赖注入，均为新生成的对象
         *
         * 结论二：
         *      如果依赖注入集合类型的对象，Singleton Bean 和 Prototype Bean 均会存在一个
         *      Prototype Bean 有别于其他地方的依赖注入 Prototype Bean
         *
         * 结论三：
         *      无论是 Singleton 还是 Prototype Bean 均会执行初始化方法回调
         *      不过仅 Singleton Bean 回字形销毁方法回调
         */

        scopedBeansByLookup(applicationContext);

        scopedBeansByInjection(applicationContext);

        applicationContext.close();
    }

    private static void scopedBeansByInjection(AnnotationConfigApplicationContext applicationContext) {

        BeanScopeDemo beanScopeDemo = applicationContext.getBean(BeanScopeDemo.class);

        System.out.println("beanScopeDemo.singletonUser = " + beanScopeDemo.singletonUser);
        System.out.println("beanScopeDemo.singletonUser1 = " + beanScopeDemo.singletonUser1);

        System.out.println("beanScopeDemo.prototypeUser = " + beanScopeDemo.prototypeUser);
        System.out.println("beanScopeDemo.prototypeUser1 = " + beanScopeDemo.prototypeUser1);
        System.out.println("beanScopeDemo.prototypeUser2 = " + beanScopeDemo.prototypeUser2);

        System.out.println("beanScopeDemo.users = " + beanScopeDemo.map);
    }

    private static void scopedBeansByLookup(AnnotationConfigApplicationContext applicationContext) {

        for(int i=0; i<3; i++) {
            // singleton 是共享对象
            User singletonUser = applicationContext.getBean("singletonUser", User.class);
            System.out.println("singletonUser = " + singletonUser);
            // prototype 是每次依赖查找均生成了新对象
            User prototypeUser = applicationContext.getBean("prototypeUser", User.class);
            System.out.println("prototypeUser = " + prototypeUser);
        }
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("当前 BeanScopeDemo Bean 正在销毁中 。。。 ");
//        this.prototypeUser.destroy();
//        this.prototypeUser1.destroy();
//        this.prototypeUser2.destroy();

        // 获取BeanDefinition
        for (Map.Entry<String, User> entry:map.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            if(beanDefinition.isPrototype()){   // 如果当前 Bean 是 prototype scope
                User user = entry.getValue();
                user.destroy();
            }
        }

        System.out.println("当前 BeanScopeDemo Bean 销毁完成");
    }
}
