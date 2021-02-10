package org.geekbang.bean.factory;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Factory;

/**
 * @className: UserFactoryBean
 * @description: User Bean 的 FactoryBean实现
 * @author: ZSZ
 * @date: 2021/2/9 13:36
 */
public class UserFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
