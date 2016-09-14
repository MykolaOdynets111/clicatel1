package com.clickatell.models.mc2.users.response.profile;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbryt on 9/2/2016.
 */
public class UserProfile {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String status;
    private String accountId;
    private List<Role> roles = new ArrayList<Role>();

    /**
     * No args constructor for use in serialization
     */
    public UserProfile() {
    }

    /**
     * @param id
     * @param lastName
     * @param accountId
     * @param status
     * @param email
     * @param roles
     * @param firstName
     */
    public UserProfile(String id, String email, String firstName, String lastName, String status, String accountId, List<Role> roles) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.accountId = accountId;
        this.roles = roles;
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

    public UserProfile withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public UserProfile withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * @return The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public UserProfile withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * @return The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserProfile withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public UserProfile withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * @return The accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * @param accountId The accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public UserProfile withAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    /**
     * @return The roles
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * @param roles The roles
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public UserProfile withRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(email).append(firstName).append(lastName).append(status).append(accountId).append(roles).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UserProfile) == false) {
            return false;
        }
        UserProfile rhs = ((UserProfile) other);
        return new EqualsBuilder().append(id, rhs.id).append(email, rhs.email).append(firstName, rhs.firstName).append(lastName, rhs.lastName).append(status, rhs.status).append(accountId, rhs.accountId).append(roles, rhs.roles).isEquals();
    }
}
