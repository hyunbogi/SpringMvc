package com.hyunbogi.springmvc.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class User {
    private long id;

    @Min(0)
    private int age;

    @Size(min = 2, max = 10)
    private String name;

    @Size(min = 1)
    private String password;

    @Size(min = 1)
    private String email;

    public User() {
    }

    public User(long id, int age, String name, String password, String email) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
