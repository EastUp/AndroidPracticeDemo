package com.east.architect_zenghui.architect17_designmode10_prototype.simple5_deep_clone;

import androidx.annotation.NonNull;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:
 *  @author: East
 *  @date: 2020-02-23
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class User implements Cloneable{
    private String name;  //拷贝的时候直接赋值过去
    private int age;       //String也是
    private Address address;


    public User() {
    }

    public User(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @NonNull
    @Override
    protected User clone() throws CloneNotSupportedException {
        User user = (User) super.clone();
        Address address = this.address.clone();
        user.address = address;
        return user;
    }

    public static class Address implements Cloneable{
        public String addressName;
        public String city;

        public Address(String addressName, String city) {
            this.addressName = addressName;
            this.city = city;
        }

        @NonNull
        @Override
        protected Address clone() throws CloneNotSupportedException {
            return (Address) super.clone();
        }
    }
}
