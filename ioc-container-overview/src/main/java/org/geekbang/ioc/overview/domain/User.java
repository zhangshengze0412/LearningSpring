package org.geekbang.ioc.overview.domain;

import org.geekbang.ioc.overview.City;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;
//import java.util.List;

/**
 * @className: User
 * @description: 用户类
 * @author: ZSZ
 * @date: 2021/2/5 11:32
 */
public class User implements BeanNameAware {

    private Long id;

    private City city;

    private City[] workCities;

    private List<City> lifeCities;

    private String name;

    private Resource configFieldLocation;

    // 当前Bean的名称
    private transient String beanName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Resource getConfigFieldLocation() {
        return configFieldLocation;
    }

    public void setConfigFieldLocation(Resource configFieldLocation) {
        this.configFieldLocation = configFieldLocation;
    }

    public City[] getWorkCities() {
        return workCities;
    }

    public void setWorkCities(City[] workCities) {
        this.workCities = workCities;
    }

    public List<City> getLifeCities() {
        return lifeCities;
    }

    public void setLifeCities(List<City> lifeCities) {
        this.lifeCities = lifeCities;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", city=" + city +
                ", workCities=" + Arrays.toString(workCities) +
                ", lifeCities=" + lifeCities +
                ", name='" + name + '\'' +
                ", configFieldLocation=" + configFieldLocation +
                '}';
    }

    public static User createUser(){
        User user = new User();
        user.setId(2l);
        user.setName("ls-by-createUser");
        return user;
    }


    @PostConstruct
    public void init(){
        System.out.println("User Bean [" + beanName + "] 初始化....");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("User Bean [" + beanName + "] 销毁....");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

}
