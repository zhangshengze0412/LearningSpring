package org.geekbang.dependency.lookup;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @className: BeanInstantiationExceptionDemo
 * @description: Bean示例化失败（抽象类或者接口）
 * @author: ZSZ
 * @date: 2021/2/10 22:42
 */
public class BeanInstantiationExceptionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(CharSequence.class);

        applicationContext.registerBeanDefinition("errorBean",builder.getBeanDefinition());

        applicationContext.refresh();

        applicationContext.close();
    }

}
