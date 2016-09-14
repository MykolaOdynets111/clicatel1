
package com.clickatell.models.mc2.roles.response.allroles;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class AllRolesResponse {

    private List<Role> roles = new ArrayList<Role>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public AllRolesResponse() {
    }

    /**
     * 
     * @param roles
     */
    public AllRolesResponse(List<Role> roles) {
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

    public AllRolesResponse withRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
