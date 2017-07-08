package com.zeroisbiggerthanone.pcs_aos.models;

public class User {

    private String id;
    private UserBase userBase;
    private Password password;

    public User() {
    }

    public User(String id, UserBase userBase, Password password) {
        this.id = id;
        this.userBase = userBase;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserBase getUserBase() {
        return userBase;
    }

    public void setUserBase(UserBase userBase) {
        this.userBase = userBase;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "com.zeroisbiggerthanone.pcs.entities.User{" +
                "id='" + id + '\'' +
                ", userBase=" + userBase +
                ", password=" + password +
                '}';
    }
}
