package com.clickatell.models.mc2.roles.request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oshchur on 08.07.2016.
 */
public class RolePermissionsIds {
    private List<String> permissionsIds = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     *
     */
    public RolePermissionsIds() {
        this.permissionsIds.add("delete-integration");
    }

    /**
     *
     * @param permissionsIds
     */
    public RolePermissionsIds(List<String> permissionsIds) {
        this.permissionsIds = permissionsIds;
    }

    /**
     *
     * @return
     * The permissionsIds
     */
    public List<String> getPermissionsIds() {
        return permissionsIds;
    }

    /**
     *
     * @param permissionsIds
     * The permissionsIds
     */
    public void setPermissionsIds(List<String> permissionsIds) {
        this.permissionsIds = permissionsIds;
    }

    public RolePermissionsIds withPermissionsIds(List<String> permissionsIds) {
        this.permissionsIds = permissionsIds;
        return this;
    }
}
