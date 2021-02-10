package org.geekbang.bean.definition;

import org.geekbang.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @className: BeanGarbageCollectionDemo
 * @description: Bean 垃圾回收（GC） 示例
 * @author: ZSZ
 * @date: 2021/2/9 21:47
 */
public class BeanGarbageCollectionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanInitializationDemo.class);
        applicationContext.refresh();

        // 关闭Spring容器
        applicationContext.close();
        System.out.println("Spring 应用上下文已关闭。。。。");
        // 强制GC
        System.gc();
    }
}
