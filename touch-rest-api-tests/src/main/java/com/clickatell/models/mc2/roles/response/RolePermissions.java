package com.clickatell.models.mc2.roles.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oshchur on 08.07.2016.
 */
public class RolePermissions {
    private List<String> permissions = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     *
     */
    public RolePermissions() {
    }

    /**
     *
     * @param permissions
     */
    public RolePermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    /**
     *
     * @return
     * The permissions
     */
    public List<String> getPermissions() {
        return permissions;
    }

    /**
     *
     * @param permissions
     * The permissions
     */
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public RolePermissions withPermissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }
}
