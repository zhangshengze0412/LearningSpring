package org.geekbang.ioc.overview.domain;

import org.geekbang.ioc.overview.annotation.Super;

/**
 * @className: SuperUser
 * @description: 超级用户
 * @author: ZSZ
 * @date: 2021/2/5 12:07
 */
@Super
public class SuperUser extends User {

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "address='" + address + '\'' +
                "}" + super.toString();
    }
}
