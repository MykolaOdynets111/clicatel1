
package com.clickatell.models.mc2.invitations.response.one_invitation;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class GetInvitationResponse {

    private String token;
    private String email;
    private String firstName;
    private String lastName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetInvitationResponse() {
    }

    /**
     * 
     * @param lastName
     * @param email
     * @param token
     * @param firstName
     */
    public GetInvitationResponse(String token, String email, String firstName, String lastName) {
        this.token = token;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * 
     * @return
     *     The token
     */
    public String getToken() {
        return token;
    }

    /**
     * 
     * @param token
     *     The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    public GetInvitationResponse withToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public GetInvitationResponse withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * 
     * @return
     *     The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public GetInvitationResponse withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GetInvitationResponse withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
