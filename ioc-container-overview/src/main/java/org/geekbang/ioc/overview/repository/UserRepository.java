package org.geekbang.ioc.overview.repository;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;

import java.util.Collection;

/**
 * @className:
 * @description:
 * @author: ZSZ
 * @date: 2021/2/5 15:33
 */
public class UserRepository {

    private Collection<User> users;     // 自定义 Bean

    private BeanFactory beanFactory;    // 内建非 Bean 对象（依赖）

    private ObjectFactory<ApplicationContext> objectFactory;      //

    public ObjectFactory<ApplicationContext> getObjectFactory() {
        return objectFactory;
    }

    public void setObjectFactory(ObjectFactory<ApplicationContext> objectFactory) {
        this.objectFactory = objectFactory;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
