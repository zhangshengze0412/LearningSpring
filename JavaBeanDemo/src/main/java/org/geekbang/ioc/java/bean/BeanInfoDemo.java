package org.geekbang.ioc.java.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyEditorSupport;
import java.util.stream.Stream;

/**
 * @className: BeanInfoDemo
 * @description:
 * @author: ZSZ
 * @date: 2021/2/5 9:12
 */
public class BeanInfoDemo {

    public static void main(String[] args) throws IntrospectionException {
        //Introspector.getBeanInfo(需要查找的类的名称,stopObject)
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class,Object.class);
        // PropertyDescriptor，存储bean的一些配置信息
        Stream.of(beanInfo.getPropertyDescriptors())
                .forEach(propertyDescriptor -> {
                    System.out.println(propertyDescriptor);
                });

        Stream.of(beanInfo.getPropertyDescriptors())
                .forEach(propertyDescriptor -> {
                    // PropertyDescriptor  允许添加属性编辑器 - PropertyEditor
                    // GUI -> text（String）-> PropertyType
                    // name -> String
                    // age -> Integer
                    Class<?> propertyType = propertyDescriptor.getPropertyType();
                    String propertyName = propertyDescriptor.getName();
                    if("age".equals(propertyName)){
                        // String to Integer
                        propertyDescriptor.setPropertyEditorClass(StringToIntegerPropertyEditor.class);
                    }
                });
    }

    static class StringToIntegerPropertyEditor extends PropertyEditorSupport{
        public void setAsText(String text) throws java.lang.IllegalArgumentException {
            Integer value = Integer.valueOf(text);
            setValue(value);
        }
    }

}
