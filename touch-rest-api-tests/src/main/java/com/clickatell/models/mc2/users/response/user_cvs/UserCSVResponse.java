package com.clickatell.models.mc2.users.response.user_cvs;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Created by oshchur on 19.07.2016.
 */
public class UserCSVResponse {
    private String name;
    private String lastName;
    private String email;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserCSVResponse() {
    }

    /**
     *
     * @param email
     * @param name
     * @param lastName
     */
    public UserCSVResponse(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     *
     * @param list
     */
    public UserCSVResponse(String[] list) {
        this.name = list[0];
        this.lastName = list[1];
        this.email = list[2];
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public UserCSVResponse withName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserCSVResponse withSurname(String surname) {
        this.lastName = surname;
        return this;
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

    public UserCSVResponse withEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UserCSVResponse) == false) {
            return false;
        }
        UserCSVResponse rhs = ((UserCSVResponse) other);
        return new EqualsBuilder().append(name, rhs.name).append(lastName, rhs.lastName).append(email, rhs.email).isEquals();
    }
}
