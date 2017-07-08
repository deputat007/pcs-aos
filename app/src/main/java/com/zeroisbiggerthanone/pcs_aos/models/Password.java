package com.zeroisbiggerthanone.pcs_aos.models;

public class Password {

    private String id;
    private String password;

    public Password() {
    }

    public Password(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "com.zeroisbiggerthanone.pcs.entities.Password{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
