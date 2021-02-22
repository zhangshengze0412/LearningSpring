package org.geekbang.ioc.bean.scope;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * @className: ThreadLocalScopeDemo
 * @description: 注册 自定义 ThreadLocalScope
 * @author: ZSZ
 * @date: 2021/2/22 17:35
 */
public class ThreadLocalScopeDemo {

    @Bean
    @Scope(ThreadLocalScope.SCOPE_NAME)
    public User user(){
        User user = new User();
        user.setId(System.nanoTime());
        return user;
    }

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register((ThreadLocalScopeDemo.class));

        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            // 注册自定义 Scope
            beanFactory.registerScope(ThreadLocalScope.SCOPE_NAME, new ThreadLocalScope());
        });

        applicationContext.refresh();

        scopedBeansByLookup(applicationContext);

        applicationContext.close();
    }

    private static void scopedBeansByLookup(AnnotationConfigApplicationContext applicationContext) throws InterruptedException {

        for(int i=0; i<3; i++) {
            Thread thread = new Thread(()->{
                User user = applicationContext.getBean("user", User.class);
                System.out.printf("[Thread id: %d] user = %s%n",Thread.currentThread().getId(),user);
            });

            thread.start();
            thread.join();
        }

    }

}
