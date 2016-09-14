
package com.clickatell.models.mc2.users.response.my_permissions;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class PerrmissionsResponse {

    private List<String> permissions = new ArrayList<String>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public PerrmissionsResponse() {
    }

    /**
     * 
     * @param permissions
     */
    public PerrmissionsResponse(List<String> permissions) {
        this.permissions = permissions;
    }

    /**
     * 
     * @return
     *     The permissions
     */
    public List<String> getPermissions() {
        return permissions;
    }

    /**
     * 
     * @param permissions
     *     The permissions
     */
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public PerrmissionsResponse withPermissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
