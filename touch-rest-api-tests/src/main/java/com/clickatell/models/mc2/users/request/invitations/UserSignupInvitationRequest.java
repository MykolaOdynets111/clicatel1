
package com.clickatell.models.mc2.users.request.invitations;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class UserSignupInvitationRequest {

    private String password;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserSignupInvitationRequest() {
    }

    /**
     * 
     * @param password
     */
    public UserSignupInvitationRequest(String password) {
        this.password = password;
    }

    /**
     * 
     * @return
     *     The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password
     *     The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public UserSignupInvitationRequest withPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
