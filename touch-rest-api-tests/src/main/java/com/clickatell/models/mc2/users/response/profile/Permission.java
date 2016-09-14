
package com.clickatell.models.mc2.users.response.profile;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Permission {

    private String id;
    private String action;
    private String subject;
    private String solution;
    private String category;

    /**
     * No args constructor for use in serialization
     */
    public Permission() {
    }

    /**
     * @param id
     * @param category
     * @param subject
     * @param action
     * @param solution
     */
    public Permission(String id, String action, String subject, String solution, String category) {
        this.id = id;
        this.action = action;
        this.subject = subject;
        this.solution = solution;
        this.category = category;
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

    public Permission withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action The action
     */
    public void setAction(String action) {
        this.action = action;
    }

    public Permission withAction(String action) {
        this.action = action;
        return this;
    }

    /**
     * @return The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Permission withSubject(String subject) {
        this.subject = subject;
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

    public Permission withSolution(String solution) {
        this.solution = solution;
        return this;
    }

    /**
     * @return The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    public Permission withCategory(String category) {
        this.category = category;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(action).append(subject).append(solution).append(category).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Permission) == false) {
            return false;
        }
        Permission rhs = ((Permission) other);
        return new EqualsBuilder().append(id, rhs.id).append(action, rhs.action).append(subject, rhs.subject).append(solution, rhs.solution).append(category, rhs.category).isEquals();
    }

}
