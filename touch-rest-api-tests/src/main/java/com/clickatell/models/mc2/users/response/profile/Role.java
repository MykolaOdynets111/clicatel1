
package com.clickatell.models.mc2.users.response.profile;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Role {

    private String id;
    private String name;
    private String description;
    private String solution;
    private Boolean admin;
    private Long createdDate;
    private List<Permission> permissions = new ArrayList<Permission>();

    /**
     * No args constructor for use in serialization
     */
    public Role() {
    }

    /**
     * @param id
     * @param admin
     * @param description
     * @param name
     * @param permissions
     * @param createdDate
     * @param solution
     */
    public Role(String id, String name, String description, String solution, Boolean admin, Long createdDate, List<Permission> permissions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.solution = solution;
        this.admin = admin;
        this.createdDate = createdDate;
        this.permissions = permissions;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public Role withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Role withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Role withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * @return The solution
     */
    public String getSolution() {
        return solution;
    }

    /**
     * @param solution The solution
     */
    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Role withSolution(String solution) {
        this.solution = solution;
        return this;
    }

    /**
     * @return The admin
     */
    public Boolean getAdmin() {
        return admin;
    }

    /**
     * @param admin The admin
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Role withAdmin(Boolean admin) {
        this.admin = admin;
        return this;
    }

    /**
     * @return The createdDate
     */
    public Long getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate The createdDate
     */
    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Role withCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    /**
     * @return The permissions
     */
    public List<Permission> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions The permissions
     */
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public Role withPermissions(List<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(description).append(solution).append(admin).append(createdDate).append(permissions).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Role) == false) {
            return false;
        }
        Role rhs = ((Role) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(description, rhs.description).append(solution, rhs.solution).append(admin, rhs.admin).append(createdDate, rhs.createdDate).append(permissions, rhs.permissions).isEquals();
    }

}
