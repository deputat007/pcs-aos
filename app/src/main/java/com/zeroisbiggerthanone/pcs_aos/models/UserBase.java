package com.zeroisbiggerthanone.pcs_aos.models;

public class UserBase {
    private String id;
    private String login;
    private Role role;
    private String phoneNumber;
    private String secretDigit;

    public UserBase() {
    }

    public UserBase(String id, String login, Role role, String phoneNumber, String secretDigit) {
        this.id = id;
        this.login = login;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.secretDigit = secretDigit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecretDigit() {
        return secretDigit;
    }

    public void setSecretDigit(String secretDigit) {
        this.secretDigit = secretDigit;
    }

    @Override
    public String toString() {
        return "com.zeroisbiggerthanone.pcs.entities.UserBase{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", role=" + role +
                ", phoneNumber=" + phoneNumber +
                ", secretDigit='" + secretDigit + '\'' +
                '}';
    }
}
