package com.zeroisbiggerthanone.pcs_aos.models;

public class Role {

    private String id;
    private String roleName;

    public Role() {
    }

    public Role(String id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "com.zeroisbiggerthanone.pcs.entities.Role{" +
                "id='" + id + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
