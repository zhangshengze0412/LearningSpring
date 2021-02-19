package org.geekbang.dependency.injection;

import org.geekbang.ioc.overview.domain.User;

/**
 * @className: UserHolder
 * @description: User的holder类
 * @author: ZSZ
 * @date: 2021/2/11 13:23
 */
public class UserHolder {

    private User user;

    public UserHolder(){}

    public UserHolder(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                '}';
    }
}
