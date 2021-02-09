package org.geekbang.ioc.java.bean;

/**
 * @className:  Person
 * @description: 描述人的POJO类
 *
 * Setter / Getter方法
 *
 * 可写方法（writable） / 可读方法（Readable）
 * @author: ZSZ
 * @date: 2021/2/5 9:08
 */
public class Person {

    // String to String
    String name; // Property

    // String to Integer
    Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
