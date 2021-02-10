package org.geekbang.bean.definition;

import org.geekbang.ioc.overview.annotation.Super;
import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @className: BeanAliasDemo
 * @description: 依赖查找
 *                  1. 通过Bean名称查找
 *                  2. 根基Bean类型查找
 *                  3. 根据Bean名称+类型查找
 *                  4. 根据
 * @author: ZSZ
 * @date: 2021/2/5 11:27
 */
public class BeanAliasDemo {

    public static void main(String[] args) {
        // 配置XML配置文件
        // 启动Spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-definition-context.xml");

        //根据Bean别名"zs-user" 查找
        User user = (User) beanFactory.getBean("user");
        User zsUser = (User) beanFactory.getBean("zs-user");
        System.out.println(user == zsUser);

    }
}
