package org.geekbang.bean.factory;

import org.geekbang.ioc.overview.domain.User;

/**
 * @className: UserFactory
 * @description: User 工厂类
 * @author: ZSZ
 * @date: 2021/2/9 13:28
 */
public interface UserFactory {
    default User createUser(){
        return User.createUser();
    }
}
