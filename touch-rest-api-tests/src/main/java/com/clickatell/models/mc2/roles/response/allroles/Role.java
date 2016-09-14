
package com.clickatell.models.mc2.roles.response.allroles;

import com.clickatell.models.mc2.roles.response.Permission;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class Role {

    private String id;
    private String name;
    private String description;
    private String solution;
    private String createdDate;
    private Boolean removable;
    private ArrayList<Permission> permissions;
    private Boolean admin;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Role() {
    }

    /**
     * 
     * @param id
     * @param description
     * @param name
     * @param solution
     */
    public Role(String id, String name, String description, String solution, Boolean removable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.solution = solution;
        this.removable = removable;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public Role withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Role withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Role withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * 
     * @return
     *     The solution
     */
    public String getSolution() {
        return solution;
    }

    /**
     * 
     * @param solution
     *     The solution
     */
    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Role withSolution(String solution) {
        this.solution = solution;
        return this;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getRemovable() {
        return removable;
    }

    public void setRemovable(Boolean removable) {
        this.removable = removable;
    }

    public Role withRemovable(Boolean removable){
        this.removable = removable;
        return this;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(ArrayList<Permission> permissions) {
        this.permissions = permissions;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (getId() != null ? !getId().equals(role.getId()) : role.getId() != null) return false;
        if (getName() != null ? !getName().equals(role.getName()) : role.getName() != null) return false;
        return getDescription() != null ? getDescription().equals(role.getDescription()) : role.getDescription() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
