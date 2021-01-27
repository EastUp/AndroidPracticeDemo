package com.dn_alan.myapplication.bean;


import com.dn_alan.myapplication.annotation.DbField;
import com.dn_alan.myapplication.annotation.DbTable;

@DbTable("tb_person")
public class Person {
    @DbField("_id")
    private Integer id;
    private String name;
    private String password;

    public Person() {
    }

    public Person(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
