package com.clickatell.models.mc2.invitations.request;

import com.clickatell.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oshchur on 14.07.2016.
 */
public class UpdateUserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateUserRequest() {
        this.email = StringUtils.generateEmail();
        this.firstName = StringUtils.generateRandomString(8);
        this.lastName = StringUtils.generateRandomString(8);
    }

    /**
     *
     * @param lastName
     * @param email
     * @param roles
     * @param firstName
     */
    public UpdateUserRequest(String email, String firstName, String lastName, List<String> roles) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public UpdateUserRequest withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     *
     * @return
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public UpdateUserRequest withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UpdateUserRequest withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     *
     * @return
     * The roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     *
     * @param roles
     * The roles
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public UpdateUserRequest withRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

}
