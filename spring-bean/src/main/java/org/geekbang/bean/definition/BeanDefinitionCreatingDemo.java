package org.geekbang.bean.definition;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * @className: BeanDefinitionCreatingDemo
 * @description: BeanDefinitionCreatingDemo 构建实例
 * @author: ZSZ
 * @date: 2021/2/9 8:22
 */
public class BeanDefinitionCreatingDemo {

    public static void main(String[] args) {
        // 1.通过BeanDefinitionBuilder构建
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        //通过属性设置
        builder.addPropertyValue("id",1)
                .addPropertyValue("name","zs");
        //获取 BeanDefinition实例
        BeanDefinition beanDefinition = builder.getBeanDefinition();
        // BeanDefinition 并非Bean终态，可以自定义修改

        // 2.通过AbstractBeanDefinition以及派生类
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        // 设置Bean类型
        genericBeanDefinition.setBeanClass(User.class);
        // 通过MutablePropertyValues 批量操作属性
        MutablePropertyValues propertyValues = new MutablePropertyValues();
//        propertyValues.addPropertyValue("id",1);
//        propertyValues.addPropertyValue("name","zs");
        propertyValues.add("id",1)
                .add("name","zs");
        // 通过set MutablePropertyValue 批量属性操作
        genericBeanDefinition.setPropertyValues(propertyValues);
    }

}
