package org.geekbang.dependency.lookup;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * @className: BeanCreationExceptionDemo
 * @description: Bean 初始化异常
 * @author: ZSZ
 * @date: 2021/2/10 22:46
 */
public class BeanCreationExceptionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(POJO.class);
        applicationContext.registerBeanDefinition("errorBean",builder.getBeanDefinition());
        applicationContext.refresh();
        applicationContext.close();
    }

    static class POJO implements InitializingBean{

        @PostConstruct
        public void init() throws Throwable{
            throw new Throwable("init() : For purposes ... ");
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            throw new Exception("afterPropertiesSet() : For purposes ... ");
        }

    }

}
