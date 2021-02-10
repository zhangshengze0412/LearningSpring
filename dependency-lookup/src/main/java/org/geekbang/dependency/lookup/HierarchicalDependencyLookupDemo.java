package org.geekbang.dependency.lookup;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @className: HierarchicalDependencyLookupDemo
 * @description: 层次性依赖查找示例
 * @author: ZSZ
 * @date: 2021/2/10 18:01
 */
public class HierarchicalDependencyLookupDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(HierarchicalDependencyLookupDemo.class);

        // 1. 获取ConfigurableListableBeanFactory <- ConfigurableBeanFactory <- HierarchicalBeanFactory
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        System.out.println("当前 BeanFactory的 Parent BeanFactory :" + beanFactory.getParentBeanFactory());

        // 2.设置 Parent BeanFactory
        HierarchicalBeanFactory parentBeanFactory = createParentBeanFactory();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        System.out.println("当前 BeanFactory的 Parent BeanFactory :" + beanFactory.getParentBeanFactory());

        displayContainsLocalBean(beanFactory,"user");
        displayContainsLocalBean(parentBeanFactory,"user");

        displayContainsBean(beanFactory,"user");
        displayContainsBean(parentBeanFactory,"user");

        applicationContext.refresh();
        applicationContext.close();
    }

    private static void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName){
        System.out.printf("当前 BeanFactory[%s] 是否包含 bean[name : %s] : %s \n",beanFactory,beanName,
                containsBean(beanFactory, beanName));
    }

    // 委托先给parentBeanFactory查找
    private static boolean containsBean(HierarchicalBeanFactory beanFactory,String beanName){
        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
        if(parentBeanFactory instanceof HierarchicalBeanFactory){
            HierarchicalBeanFactory parentHierarchicalBeanFactory = HierarchicalBeanFactory.class.cast(parentBeanFactory);
            if(containsBean(parentHierarchicalBeanFactory,beanName)){
                return true;
            }
        }
        return beanFactory.containsLocalBean(beanName);
    }

    private static void displayContainsLocalBean(HierarchicalBeanFactory beanFactory, String beanName){
        System.out.printf("当前 BeanFactory[%s] 是否包含 Local Bean[name : %s] : %s \n",beanFactory,beanName,
                beanFactory.containsLocalBean(beanName));
    }

    private static ConfigurableListableBeanFactory createParentBeanFactory(){
        // 创建BeanFactory 容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "classpath:/META-INF/dependency-lookup-context.xml";
        reader.loadBeanDefinitions(location);
        return beanFactory;
    }


}
