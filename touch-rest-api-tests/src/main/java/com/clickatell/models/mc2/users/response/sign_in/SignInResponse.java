
package com.clickatell.models.mc2.users.response.sign_in;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class SignInResponse {

    private String token;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SignInResponse() {
    }

    /**
     * 
     * @param token
     */
    public SignInResponse(String token) {
        this.token = token;
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

    public SignInResponse withToken(String token) {
        this.token = token;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
