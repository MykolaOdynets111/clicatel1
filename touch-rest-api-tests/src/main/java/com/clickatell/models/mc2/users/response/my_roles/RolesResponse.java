
package com.clickatell.models.mc2.users.response.my_roles;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class RolesResponse {

    private List<Role> roles = new ArrayList<Role>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public RolesResponse() {
    }

    /**
     * 
     * @param roles
     */
    public RolesResponse(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * 
     * @return
     *     The roles
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * 
     * @param roles
     *     The roles
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public RolesResponse withRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
