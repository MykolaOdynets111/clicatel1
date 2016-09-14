package com.clickatell.models.mc2.roles.response;

/**
 * Created by oshchur on 05.07.2016.
 */
public class RoleId {
    private String roleId;

    /**
     * No args constructor for use in serialization
     *
     */
    public RoleId() {
    }

    /**
     *
     * @param roleId
     */
    public RoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     *
     * @return
     * The roleId
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     *
     * @param roleId
     * The roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public RoleId withRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }
}
