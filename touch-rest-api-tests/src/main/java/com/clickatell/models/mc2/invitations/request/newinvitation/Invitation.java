
package com.clickatell.models.mc2.invitations.request.newinvitation;

import com.clickatell.utils.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class Invitation {

    private String targetEmail;
    private String firstName;
    private String lastName;
    private List<String> roleIds = new ArrayList<String>();

    /**
     * No args constructor for use in serialization
     */
    public Invitation() {
    }

    public Invitation(String targetEmail) {
        this.targetEmail = targetEmail;
        firstName = "FirstName_" + StringUtils.generateRandomString(10);
        lastName = "LastName_" + StringUtils.generateRandomString(10);
    }

    /**
     * @param lastName
     * @param targetEmail
     * @param firstName
     * @param roleIds
     */
    public Invitation(String targetEmail, String firstName, String lastName, List<String> roleIds) {
        this.targetEmail = targetEmail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleIds = roleIds;
    }

    /**
     * @return The targetEmail
     */
    public String getTargetEmail() {
        return targetEmail;
    }

    /**
     * @param targetEmail The targetEmail
     */
    public void setTargetEmail(String targetEmail) {
        this.targetEmail = targetEmail;
    }

    public Invitation withTargetEmail(String targetEmail) {
        this.targetEmail = targetEmail;
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

    public Invitation withFirstName(String firstName) {
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

    public Invitation withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * @return The roleIds
     */
    public List<String> getRoleIds() {
        return roleIds;
    }

    /**
     * @param roleIds The roleIds
     */
    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public Invitation withRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
